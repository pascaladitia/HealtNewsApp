package com.pascal.healtnewsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.utils.SessionManager
import com.pascal.healtnewsapp.view.login.LoginActivity
import com.pascal.healtnewsapp.view.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val session = SessionManager(this)

        Handler().postDelayed(Runnable {

            if (session.isLogin ?: true) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 2000)
    }
}