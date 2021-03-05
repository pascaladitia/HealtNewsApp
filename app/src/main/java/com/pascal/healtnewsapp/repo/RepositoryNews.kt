package com.pascal.healtnewsapp.repo

import android.content.Context
import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.model.ResponseNews
import com.pascal.healtnewsapp.network.ConfigNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryNews(context: Context) {

    fun getData(
        responHandler: (ResponseNews) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.service().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getDatabyUser(
        id: String,
        responHandler: (ResponseNews) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.service().getDatabyUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun hapusData(
        id: String,
        responHandler: (ResponseAction) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.service().deleteData(id ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun inputData(
        title: String, desk: String, author: String, user_id: String,
        image: String, responHandler: (ResponseAction) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.service().insertData(title, desk, author, user_id, image)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun updateData(
        id: String, title: String, desk: String, author: String,
        user_id: String, image: String, responHandler: (ResponseAction) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.service().updateData(id, title, desk, author, user_id, image)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }
}