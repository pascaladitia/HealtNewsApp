package com.pascal.healtnewsapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ConfigNetwork {

    companion object {

        fun service(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttp = OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl("http://192.168.43.125/news/")
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}