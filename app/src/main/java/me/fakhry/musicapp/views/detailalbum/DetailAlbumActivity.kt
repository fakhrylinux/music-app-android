package me.fakhry.musicapp.views.detailalbum

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import me.fakhry.musicapp.adapter.SongsAlbumAdapter
import me.fakhry.musicapp.databinding.ActivityDetailAlbumBinding
import me.fakhry.musicapp.models.Album
import me.fakhry.musicapp.models.Song
import me.fakhry.musicapp.utils.hide
import me.fakhry.musicapp.utils.visible
import me.fakhry.musicapp.views.playsong.PlaySongActivity
import org.jetbrains.anko.startActivity

class DetailAlbumActivity : AppCompatActivity() {
    companion object {
        const val KEY_ALBUM = "key_album"
    }

    private lateinit var bd: ActivityDetailAlbumBinding
    private lateinit var songsAlbumAdapter: SongsAlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityDetailAlbumBinding.inflate(layoutInflater)
        setContentView(bd.root)

        init()
        showLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            getData()
        }, 2000)
        onClick()
    }

    private fun getData() {
        if (intent != null) {
            val album = intent.getParcelableExtra<Album>(KEY_ALBUM)
            if (album != null) {
                hideLoading()
                initView(album)
            } else {
                hideLoading()
                Toast.makeText(this, "Data Null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView(album: Album) {
        bd.tvNameAlbum.text = album.nameAlbum
        bd.tvArtistAlbum.text = album.artistAlbum
        bd.tvReleaseAlbum.text = album.getAlbum()
        Glide.with(this)
            .load(album.imageAlbum)
            .placeholder(android.R.color.darker_gray)
            .into(bd.ivDetailAlbum)

        showSongsAlbums(album.songs)
    }

    private fun showSongsAlbums(songs: List<Song>?) {
        if (songs != null) {
            songsAlbumAdapter.setData(songs as MutableList<Song>)
            bd.rvDetailAlbum.adapter = songsAlbumAdapter
        } else {
            Toast.makeText(this, "Data Null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClick() {
        bd.tbDetailAlbum.setNavigationOnClickListener {
            finish()
        }

        songsAlbumAdapter.onClick { songs, position ->
            startActivity<PlaySongActivity>(
                PlaySongActivity.KEY_SONGS to songs,
                PlaySongActivity.KEY_POSITION to position
            )
        }
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(bd.tbDetailAlbum)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        // Init songs album adapter
        songsAlbumAdapter = SongsAlbumAdapter()
    }

    private fun showLoading() {
        bd.swipeDetailAlbum.visible()
    }

    private fun hideLoading() {
        bd.swipeDetailAlbum.hide()
    }
}