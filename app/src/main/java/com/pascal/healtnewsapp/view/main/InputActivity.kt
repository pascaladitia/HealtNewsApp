package com.pascal.healtnewsapp.view.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.DataNews
import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.utils.FilePath
import com.pascal.healtnewsapp.viewModel.ViewModelNews
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.dialog_choose_image.view.*
import java.io.*
import kotlin.random.Random


class InputActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelNews
    private var item: DataNews? = null
    private var userId: String? = null
    private var image_path: String? = null
    private var dialog: Dialog? = null

    private val CAMERA_CODE = 1
    private val GALLERY_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        initView()
        attachObserve()
        initBtn()
    }

    private fun initBtn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        }

        input_image.setOnClickListener {
            showDialog()
        }
    }

    private fun attachObserve() {
        viewModel.responseInput.observe(this, Observer { inputData(it) })
        viewModel.responseUpdate.observe(this, Observer { updateData(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.isEmpty.observe(this, Observer { showEmpty(it) })
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ViewModelNews::class.java)
        item = intent.getParcelableExtra("data")
        userId = intent.getStringExtra("userId")

        if (item != null) {
            input_title.setText(item?.newsTitle)
            input_desk.setText(item?.newsDesk)
            input_author.setText(item?.newsAuthor)

            btn_inputSave.text = "Update"
        }

        when (btn_inputSave.text) {
            "Update" -> {
                btn_inputSave.setOnClickListener {
                    if (image_path != null) {
                        viewModel.updateNewsView(
                            item?.newsId!!,
                            input_title.text.toString(),
                            input_desk.text.toString(),
                            input_author.text.toString(),
                            item?.userId!!,
                            "data:image/jpeg;base64, ${encodeBase64(image_path.toString())}")
                    } else {
                        showToast("Gambar tidak boleh kosong")
                    }
                }
            }
            else -> {
                btn_inputSave.setOnClickListener {
                    if (image_path != null) {
                        viewModel.inputNewsView(
                            input_title.text.toString(),
                            input_desk.text.toString(),
                            input_author.text.toString(),
                            userId.toString(),
                            "data:image/jpeg;base64, ${encodeBase64(image_path.toString())}"
                        )
                    } else {
                        showToast("Gambar tidak boleh kosong")
                    }

                }
            }
        }
    }

    private fun showError(it: Throwable?) {
        showToast(it?.message.toString())
    }

    private fun showEmpty(it: Boolean?) {
        if (it == true) {
            showToast("data harus di isi semua")
        }
    }

    private fun updateData(it: ResponseAction?) {
        showToast("Update Completed")
        finish()
    }

    private fun inputData(it: ResponseAction?) {
        showToast("Input Completed")
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        dialog?.dismiss()

        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            resultCamera(data)

        } else if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            resultGallery(data)

        }
    }

    private fun resultGallery(data: Intent?) {
        val image_bitmap = selectFromGalleryResult(data)
        input_image.setImageBitmap(image_bitmap)
    }

    @SuppressLint("NewApi")
    private fun selectFromGalleryResult(data: Intent?): Bitmap {
        var bm: Bitmap? = null
        if (data!=null){
            try {
                image_path = data.data?.let { FilePath.getPath(this, it) }

                Log.d("image", "${image_path}")
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }

        return bm!!
    }

    private fun resultCamera(data: Intent?) {

        val image = data?.extras?.get("data")
        val random = Random.nextInt(0, 999999)
        val name_file = "Camera$random"

        image_path = persistImage(image as Bitmap, name_file)

        input_image.setImageBitmap(BitmapFactory.decodeFile(image_path))
    }

    private fun persistImage(bitmap: Bitmap, name: String): String {

        val filesDir = filesDir
        val imageFile = File(filesDir, "${name}.png")
        val image_path = imageFile.path

        val os: OutputStream?
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e("TAG", "persistImage: ${e.message.toString()} ", e)
        }

        return image_path
    }


    private fun showDialog() {
        val window = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_image, null)
        window.setView(view)

        view.dialog_image.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_CODE)
        }

        view.dialog_gallery.setOnClickListener {

            val mimeType = arrayOf("image/jpg", "image/jpeg", "image/png")
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivityForResult(Intent.createChooser(intent, "Choose image"), GALLERY_CODE)

        }

        dialog = window.create()
        dialog?.show()
    }

    private fun convertByteArray(file_path: String): ByteArray {
        val bitmap = BitmapFactory.decodeFile(file_path)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    private fun encodeBase64(file_path: String): String {
        return Base64.encodeToString(convertByteArray(file_path), Base64.DEFAULT)
    }
}