package me.fakhry.musicapp.views.changepassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var changePasswordBinding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changePasswordBinding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(changePasswordBinding.root)

        init()
        onClick()
    }

    private fun onClick() {
        changePasswordBinding.tbChangePassword.setNavigationOnClickListener {
            finish()
        }

        changePasswordBinding.btnUpdate.setOnClickListener {
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(changePasswordBinding.tbChangePassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}