package com.pascal.healtnewsapp.view.main.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.DataNews
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_list.view.*

class DetailActivity : AppCompatActivity() {

    private var item: DataNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initView()
    }

    private fun initView() {
        item = intent.getParcelableExtra("data")

        detail_title.setText(item?.newsTitle)
        detail_tgl.setText(item?.newsTgl)
        detail_author.setText(item?.newsAuthor)
        detail_desk.setText(item?.newsDesk)

        Glide.with(this)
            .load(item?.newsImage)
            .apply(
                RequestOptions()
                    .override(200,200)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH))
            .into(detail_image)
    }
}