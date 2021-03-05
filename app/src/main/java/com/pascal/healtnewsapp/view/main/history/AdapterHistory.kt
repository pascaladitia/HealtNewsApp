package com.pascal.healtnewsapp.view.main.history

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
import kotlinx.android.synthetic.main.item_list.view.*

class AdapterHistory(
    private val data: List<DataNews>?,
    private val itemClick: OnClickListener
) : RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: DataNews?) {
            view.item_title.text = item?.newsTitle
            view.item_tgl.text = item?.newsTgl
            view.item_desk.text = item?.newsDesk

            Glide.with(itemView.context)
                .load(item?.newsImage)
                .apply(
                    RequestOptions()
                        .override(200,200)
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .into(itemView.item_image)

            view.setOnClickListener {
                itemClick.detail(item)
            }

            view.item_delete.setOnClickListener {
                itemClick.delete(item)
            }

            view.item_update.setOnClickListener {
                itemClick.update(item)
            }
        }
    }

    interface OnClickListener {
        fun detail(item: DataNews?)
        fun update(item: DataNews?)
        fun delete(item: DataNews?)
    }
}