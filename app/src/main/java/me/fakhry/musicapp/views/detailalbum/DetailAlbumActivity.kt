package me.fakhry.musicapp.views.detailalbum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityDetailAlbumBinding

class DetailAlbumActivity : AppCompatActivity() {
    private lateinit var detailAlbumBinding: ActivityDetailAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailAlbumBinding = ActivityDetailAlbumBinding.inflate(layoutInflater)
        setContentView(detailAlbumBinding.root)

        init()
        onClick()
    }

    private fun onClick() {
        detailAlbumBinding.tbDetailAlbum.setNavigationOnClickListener {
            finish()
        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(detailAlbumBinding.tbDetailAlbum)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}