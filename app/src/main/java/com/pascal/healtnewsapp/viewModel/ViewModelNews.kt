package com.pascal.healtnewsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.model.ResponseNews
import com.pascal.healtnewsapp.repo.RepositoryNews

class ViewModelNews(application: Application) : AndroidViewModel(application) {

    val repository = RepositoryNews(application.applicationContext)

    var responseNews = MutableLiveData<ResponseNews>()
    var responseDatabyUser = MutableLiveData<ResponseNews>()
    var responseDelete = MutableLiveData<ResponseAction>()
    var responseInput = MutableLiveData<ResponseAction>()
    var responseUpdate = MutableLiveData<ResponseAction>()
    var isError = MutableLiveData<Throwable>()
    var isEmpty = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()

    fun getNewsView() {
        isLoading.value = true

        repository.getData({
            isLoading.value = false
            responseNews.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }

    fun deleteNewsView(id: String) {
        isLoading.value = true

        repository.hapusData(id, {
            isLoading.value = false
            responseDelete.value = it
            getNewsView()
        }, {
            isLoading.value = false
            isError.value = it
        })
    }

    fun inputNewsView(
        title: String, desk: String,
        author: String, user_id: String, image: String
    ) {

        if (title.isNotEmpty() && desk.isNotEmpty()
            && author.isNotEmpty() && image.isNotEmpty()
        ) {
            repository.inputData(title, desk, author, user_id, image, {
                responseInput.value = it
            }, {
                isError.value = it
            })
        } else {
            isEmpty.value = true
        }
    }

    fun updateNewsView(
        id: String, title: String, desk: String,
        author: String, user_id: String, image: String
    ) {

        if (title.isNotEmpty() && desk.isNotEmpty()
            && author.isNotEmpty() && image.isNotEmpty()
        ) {
            repository.updateData(id, title, desk, author, user_id, image, {
                responseUpdate.value = it
            }, {
                isError.value = it
            })
        } else {
            isEmpty.value = true
        }
    }

    fun getDatabyUser(id: String) {
        isLoading.value = true

        repository.getDatabyUser(id, {
            isLoading.value = false
            responseDatabyUser.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}