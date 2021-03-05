package com.pascal.healtnewsapp.network

import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.model.ResponseLogin
import com.pascal.healtnewsapp.model.ResponseNews
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.*

interface ApiService {

    //login
    @FormUrlEncoded
    @POST("login.php")
    fun loginData(
        @Field("user_email") email: String,
        @Field("user_password") password: String
    ): Flowable<ResponseLogin>

    //register
    @FormUrlEncoded
    @POST("register.php")
    fun registerData(
        @Field("user_email") email: String,
        @Field("user_password") password: String
    ): Flowable<ResponseAction>


    //getData
    @GET("content/getData.php")
    fun getData(): Flowable<ResponseNews>

    //getDatabyUser
    @GET("content/getDatabyUser.php?user_id=")
    fun getDatabyUser(
        @Query("user_id") id: String?
    ): Flowable<ResponseNews>

    //insert
    @FormUrlEncoded
    @POST("content/insert.php")
    fun insertData(
        @Field("news_title") title: String,
        @Field("news_desk") desk: String,
        @Field("news_author") author: String,
        @Field("user_id") user_id: String,
        @Field("news_image") image: String
    ): Flowable<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("content/update.php")
    fun updateData(
        @Field("news_id") id: String,
        @Field("news_title") title: String,
        @Field("news_desk") desk: String,
        @Field("news_author") author: String,
        @Field("user_id") user_id: String,
        @Field("news_image") image: String
    ): Flowable<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("content/delete.php")
    fun deleteData(@Field("news_id") id: String): Flowable<ResponseAction>
}