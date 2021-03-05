package com.pascal.healtnewsapp.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.viewModel.ViewModelLogin
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        attachObserve()
    }

    private fun attachObserve() {
        viewModel.responseRegister.observe(this, Observer { showRegister(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.isEmpty.observe(this, Observer { showEmpty(it) })
        viewModel.isCorect.observe(this, Observer { showCorect(it) })
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ViewModelLogin::class.java)

        btn_cancel.setOnClickListener {
            onBackPressed()
        }

        btn_register.setOnClickListener {
            val email = register_email.text.toString()
            val password = register_passwrod.text.toString()
            val password2 = register_passwrod2.text.toString()

            viewModel.registerDataView(email, password, password2)
        }
    }

    private fun showRegister(it: ResponseAction?) {
        if (it?.isSuccess == true) {
            onBackPressed()
            finish()
            showToast("Register Completed")
        } else {
            showToast("Register Failed")
        }
    }

    private fun showError(it: Throwable?) {
        showToast(it.toString())
    }

    private fun showCorect(it: Boolean?) {
        if (it == true) {
            showToast("Password tidak cocok")
        }
    }

    private fun showEmpty(it: Boolean?) {
        if (it == true) {
            showToast("Tidak boleh ada yang kosong")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}