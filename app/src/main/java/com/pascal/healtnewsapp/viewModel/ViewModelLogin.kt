package com.pascal.healtnewsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pascal.healtnewsapp.model.ResponseAction
import com.pascal.healtnewsapp.model.ResponseLogin
import com.pascal.healtnewsapp.repo.RepositoryLogin

class ViewModelLogin(application: Application) : AndroidViewModel(application) {

    val repository = RepositoryLogin(application.applicationContext)

    var responseLogin = MutableLiveData<ResponseLogin>()
    var responseRegister = MutableLiveData<ResponseAction>()
    var isError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()
    var isEmpty = MutableLiveData<Boolean>()
    var isCorect = MutableLiveData<Boolean>()

    fun loginDataView(email: String, password: String) {
        isLoading.value = true

        if (email.isNotEmpty() && password.isNotEmpty()) {
            isLoading.value = false
            repository.loginData(email, password, {
                isLoading.value = false
                responseLogin.value = it
            }, {
                isLoading.value = false
                isError.value = it
            })
        } else {
            isLoading.value = false
            isEmpty.value = true
        }
    }

    fun registerDataView(email: String, password: String, password2: String) {
        isLoading.value = true

        if (email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()) {
            isLoading.value = false
            if (password.equals(password2)) {
                isLoading.value = false
                repository.registerData(email, password, {
                    isLoading.value = false
                    responseRegister.value = it
                }, {
                    isLoading.value = false
                    isError.value = it
                })
            } else {
                isLoading.value = false
                isCorect.value = true
            }
        } else {
            isLoading.value = false
            isEmpty.value = true
        }
    }
}