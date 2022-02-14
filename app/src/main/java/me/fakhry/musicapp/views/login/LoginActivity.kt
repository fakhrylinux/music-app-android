package me.fakhry.musicapp.views.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityLoginBinding
import me.fakhry.musicapp.views.forgotpassword.ForgotPasswordActivity
import me.fakhry.musicapp.views.main.MainActivity
import me.fakhry.musicapp.views.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        onClick()
    }

    private fun onClick() {
        loginBinding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        loginBinding.btnNewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginBinding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}