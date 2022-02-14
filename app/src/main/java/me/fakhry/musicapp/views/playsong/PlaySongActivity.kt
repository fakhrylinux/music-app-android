package me.fakhry.musicapp.views.playsong

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivityPlaySongBinding

class PlaySongActivity : AppCompatActivity() {
    private lateinit var playSongBinding: ActivityPlaySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playSongBinding = ActivityPlaySongBinding.inflate(layoutInflater)
        setContentView(playSongBinding.root)

        init()
        onClick()
    }

    private fun onClick() {
        playSongBinding.tbPlaySong.setNavigationOnClickListener {
            finish()
        }

        playSongBinding.btnPrevSong.setOnClickListener {

        }

        playSongBinding.btnPlaySong.setOnClickListener {

        }

        playSongBinding.btnNextSong.setOnClickListener {

        }

        playSongBinding.btnAddTrack.setOnClickListener {

        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(playSongBinding.tbPlaySong)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }
}