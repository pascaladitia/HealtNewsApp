package com.pascal.healtnewsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseNews(

	@field:SerializedName("data")
	val data: List<DataNews>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
) : Parcelable

@Parcelize
data class DataNews(

	@field:SerializedName("news_image")
	val newsImage: String? = null,

	@field:SerializedName("news_tgl")
	val newsTgl: String? = null,

	@field:SerializedName("news_author")
	val newsAuthor: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("news_title")
	val newsTitle: String? = null,

	@field:SerializedName("news_id")
	val newsId: String? = null,

	@field:SerializedName("news_desk")
	val newsDesk: String? = null
) : Parcelable
