package com.pascal.healtnewsapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.utils.SessionManager
import com.pascal.healtnewsapp.view.login.LoginActivity
import com.pascal.healtnewsapp.view.main.history.HistoryActivity
import com.pascal.healtnewsapp.view.main.news.NewsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val session = SessionManager(this)

        if (userId != null) {
            userId = intent.getStringExtra("data")
        } else {
            userId = session.id
        }

        dashboard_berita.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        dashboard_upload.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        dashboard_history.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        dashboard_about.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            session.logout()
        }
    }
}