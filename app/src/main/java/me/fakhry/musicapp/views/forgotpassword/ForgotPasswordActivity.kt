package me.fakhry.musicapp.views.forgotpassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(forgotPasswordBinding.root)

        init()
        onClick()
    }

    private fun onClick() {
        forgotPasswordBinding.tbForgotPassword.setNavigationOnClickListener {
            finish()
        }

        forgotPasswordBinding.btnForgotPassword.setOnClickListener {
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(forgotPasswordBinding.tbForgotPassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}