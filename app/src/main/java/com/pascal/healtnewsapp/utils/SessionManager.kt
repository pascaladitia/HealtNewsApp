package com.pascal.healtnewsapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager
    (internal var c: Context) {
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor

    internal var PRIVATE_MODE = 0

    //0 agar cuma bsa dibaca hp itu sendiri
    internal var PREF_NAME = "LOGIN"

    var isLOGIN = "isLogin"

    //mendapatkan token
    var token: String? = null
        get() = pref.getString(KEY_TOKEN, "")
    var tokenRefresh: String? = null
        get() = pref.getString(KEY_TOKEN_REFRESH, "")
    val regId: String?
        get() = pref.getString(REG_ID, "")
    val deviceId: String?
        get() = pref.getString(DEVICE_ID, "")
    val isLaunch: String?
        get() = pref.getString(IS_LAUNCH, "")

    var isLogin: Boolean?
        get() = pref.getBoolean(isLOGIN, false)
        set(isLogin) {
            editor.putBoolean(isLOGIN, true)
            editor.commit()
        }

    var email: String?
        get() = pref.getString("email", "")
        set(email) {
            editor.putString("email", email)
            editor.commit()
        }
    var id: String?
        get() = pref.getString("id", "")
        set(id) {
            editor.putString("id", id)
            editor.commit()
        }

    init {
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    //membuat session login
    fun createLoginSession(token: String, token_refresh: String) {
        editor.putString(KEY_TOKEN, token)
        editor.putBoolean(KEY_LOGIN, true)
        editor.putString(KEY_TOKEN_REFRESH, token_refresh)
        editor.commit()
        editor.apply()
        //commit digunakan untuk menyimpan perubahan
    }

    fun createRegId(regId: String) {
        editor.putString(REG_ID, regId)
        editor.commit()
        //commit digunakan untuk menyimpan perubahan
    }

    fun createDeviceId(deviceId: String) {
        editor.putString(DEVICE_ID, deviceId)
        editor.commit()
        //commit digunakan untuk menyimpan perubahan
    }

    fun setlaunch() {
        editor.putString(IS_LAUNCH, "launched")
        editor.commit()
        //commit digunakan untuk menyimpan perubahan
    }


    fun extApp(){
        editor.putString(IS_EXIST, "")
        editor.commit()

    }
    //logout user
    fun logout() {
        editor.clear()
        editor.commit()
    }


    companion object {
        private val KEY_TOKEN = "tokenLogin"
        private val KEY_TOKEN_REFRESH = "tokenRefresh"
        private val KEY_LOGIN = "isLogin"
        private val REG_ID = "regId"
        private val DEVICE_ID = "deviceId"
        private val IS_LAUNCH = "isLaunch"
        private val IS_EXIST = "isExist"
    }


}