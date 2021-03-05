package com.pascal.healtnewsapp.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.pascal.healtnewsapp.R
import com.pascal.healtnewsapp.model.ResponseLogin
import com.pascal.healtnewsapp.utils.SessionManager
import com.pascal.healtnewsapp.view.main.MainActivity
import com.pascal.healtnewsapp.viewModel.ViewModelLogin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_email

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelLogin
    private lateinit var sharePref: SharedPreferences

    companion object {
        const val NAME = "LOGIN"
        const val LOGIN_SESSION = "login_session"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        attachObserve()
    }

    private fun attachObserve() {
        viewModel.responseLogin.observe(this, Observer { showLogin(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.isLoading.observe(this, Observer { showLoading(it) })
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ViewModelLogin::class.java)
        sharePref = getSharedPreferences(NAME, Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_passwrod.text.toString()

            viewModel.loginDataView(email, password)
        }

//        if (sharePref.getInt(LOGIN_SESSION, 0) == 1) {
//            startActivity(Intent(this, MainActivity::class.java))
//        }

        btnLogin_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showLogin(it: ResponseLogin?) {
        if (it?.isSuccess == true) {
            val user = it.data

            val session = SessionManager(this)
            session.id = user?.userId
            session.isLogin = true

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("data", it?.data?.userId)
            startActivity(intent)
            finish()

            showToast("Login Completed")
        } else {
            showToast("Email atau password salah")
        }

//        sharePref.edit().putInt(LOGIN_SESSION, 1).commit()
    }

    private fun showError(it: Throwable?) {
        showToast("Login Failed")
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progress_login.visibility = View.VISIBLE
        } else {
            progress_login.visibility = View.GONE
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        attachObserve()
    }
}