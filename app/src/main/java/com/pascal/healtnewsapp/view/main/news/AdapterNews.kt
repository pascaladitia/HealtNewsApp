package com.pascal.healtnewsapp.view.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.DataNews
import kotlinx.android.synthetic.main.item_news.view.*

class AdapterNews(
    private val data: List<DataNews>?,
    private val itemClick: OnClickListener
) : RecyclerView.Adapter<AdapterNews.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: DataNews?) {
            view.itemNews_title.text = item?.newsTitle
            view.itemNews_tgl.text = item?.newsTgl
            view.itemNews_desk.text = item?.newsDesk

            Glide.with(itemView.context)
                .load(item?.newsImage)
                .apply(
                    RequestOptions()
                        .override(200,200)
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .into(itemView.itemNews_image)

            view.setOnClickListener {
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun detail(item: DataNews?)
    }
}