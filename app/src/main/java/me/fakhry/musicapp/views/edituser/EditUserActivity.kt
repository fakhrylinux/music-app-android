package me.fakhry.musicapp.views.edituser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityEditUserBinding

class EditUserActivity : AppCompatActivity() {
    private lateinit var bd: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(bd.root)

        init()
        onClick()
    }

    private fun onClick() {
        bd.btnUpdate.setOnClickListener {
            Toast.makeText(this, "Berhasil diupdate", Toast.LENGTH_SHORT).show()
        }

        bd.tbEditUser.setNavigationOnClickListener {
            finish()
        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(bd.tbEditUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}