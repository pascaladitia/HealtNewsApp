package com.pascal.healtnewsapp.view.main.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.DataNews
import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.model.ResponseNews
import com.pascal.healtnewsapp.viewModel.ViewModelNews
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelNews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        initView()
        attachObserve()
    }

    private fun attachObserve() {
        viewModel.responseNews.observe(this, Observer { showNewsView(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.isLoading.observe(this, Observer { showLoading(it) })
        viewModel.responseDelete.observe(this, Observer { showDelete(it) })
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ViewModelNews::class.java)
        viewModel.getNewsView()
    }

    private fun showNewsView(it: ResponseNews?) {
        val adapter = AdapterNews(
            it?.data,
            object :
                AdapterNews.OnClickListener {

                override fun detail(item: DataNews?) {
                    val intent = Intent(this@NewsActivity, DetailActivity::class.java)
                    intent.putExtra("data", item)
                    startActivity(intent)
                }
            })
        recycler_news.adapter = adapter
    }

    private fun showDelete(it: ResponseAction) {
        showToast("Delete Completed")
    }

    private fun showError(it: Throwable?) {
        showToast(it.toString())
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progress_news.visibility = View.VISIBLE
        } else {
            progress_news.visibility = View.GONE
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNewsView()
    }
}