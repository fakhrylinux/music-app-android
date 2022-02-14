package me.fakhry.musicapp.views.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        init()
    }

    private fun onClick() {
        registerBinding.tbRegister.setNavigationOnClickListener {
            finish()
        }

        registerBinding.btnAlreadyMemberLogin.setOnClickListener {
            finish()
        }

        registerBinding.btnRegister.setOnClickListener {
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        // Setup Support Action Bar
        setSupportActionBar(registerBinding.tbRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}