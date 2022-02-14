package me.fakhry.musicapp.views.edituser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityEditUserBinding

class EditUserActivity : AppCompatActivity() {
    private lateinit var editUserBinding: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editUserBinding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(editUserBinding.root)

        init()
        onClick()
    }

    private fun onClick() {
        editUserBinding.btnUpdate.setOnClickListener {
            Toast.makeText(this, "Berhasil diupdate", Toast.LENGTH_SHORT).show()
        }

        editUserBinding.tbEditUser.setNavigationOnClickListener {
            finish()
        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(editUserBinding.tbEditUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}