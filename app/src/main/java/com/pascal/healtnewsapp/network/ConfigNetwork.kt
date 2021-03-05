package com.pascal.healtnewsapp.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ConfigNetwork {

    fun getNews(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.43.125/news/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    fun service(): ApiService = getNews().create(ApiService::class.java)
}