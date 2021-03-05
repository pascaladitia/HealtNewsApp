package com.pascal.healtnewsapp.view.main.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.*
import com.pascal.healtnewsapp.view.main.InputActivity
import com.pascal.healtnewsapp.view.main.news.DetailActivity
import com.pascal.healtnewsapp.viewModel.ViewModelNews
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private var userId: String? = null
    private lateinit var viewModel: ViewModelNews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        initView()
        attachObserve()
    }

    private fun attachObserve() {
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.isLoading.observe(this, Observer { showLoading(it) })
        viewModel.responseDelete.observe(this, Observer { showDelete(it) })
        viewModel.responseDatabyUser.observe(this, Observer { showNewsView(it) })
    }

    private fun initView() {
        userId = intent.getStringExtra("userId")

        viewModel = ViewModelProviders.of(this).get(ViewModelNews::class.java)
        viewModel.getDatabyUser(userId!!)
    }

    private fun showNewsView(it: ResponseNews?) {
        val adapter = AdapterHistory(
            it?.data,
            object :
                AdapterHistory.OnClickListener {

                override fun detail(item: DataNews?) {
                    val intent = Intent(this@HistoryActivity, DetailActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                }

                override fun update(item: DataNews?) {
                    val intent = Intent(this@HistoryActivity, InputActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                }

                override fun delete(item: DataNews?) {
                    this@HistoryActivity?.let { it1 ->
                        AlertDialog.Builder(it1).apply {
                            setTitle("Delete")
                            setMessage("Are you sure?")
                            setPositiveButton("yes") { dialogInterface, i ->

                                viewModel.deleteNewsView(item?.newsId ?: "")
                                dialogInterface.dismiss()

                                viewModel.getDatabyUser(userId!!)
                            }
                            setNegativeButton(R.string.cancel) { dialogInterface, i ->
                                dialogInterface.dismiss()
                            }
                        }.show()
                    }
                }
            })
        recycler_history.adapter = adapter
    }

    private fun showDelete(it: ResponseAction) {
        showToast(it.message.toString())
    }

    private fun showError(it: Throwable?) {
        showToast(it.toString())
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progress_history.visibility = View.VISIBLE
        } else {
            progress_history.visibility = View.GONE
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        userId = intent.getStringExtra("userId")
        viewModel.getDatabyUser(userId!!)
    }
}