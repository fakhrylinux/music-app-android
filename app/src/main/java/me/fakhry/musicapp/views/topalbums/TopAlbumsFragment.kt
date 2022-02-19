package me.fakhry.musicapp.views.topalbums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.fakhry.musicapp.adapter.TopAlbumsAdapter
import me.fakhry.musicapp.databinding.FragmentTopAlbumsBinding
import me.fakhry.musicapp.models.Album
import me.fakhry.musicapp.utils.hide
import me.fakhry.musicapp.utils.visible
import me.fakhry.musicapp.views.detailalbum.DetailAlbumActivity
import org.jetbrains.anko.startActivity

class TopAlbumsFragment : Fragment() {
    private var _binding: FragmentTopAlbumsBinding? = null
    private val topAlbumsBinding get() = _binding
    private lateinit var topAlbumsAdapter: TopAlbumsAdapter
    private lateinit var databaseTopAlbums: DatabaseReference

    private val eventListenerTopAlbums = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            Log.d("TopAlbumsFragment", "[onDataChange-snapshot] ${snapshot.value}")

            val gson = Gson().toJson(snapshot.value)
            Log.d("TopAlbumsFragment", "[onDataChange-gson] $gson")

            val type = object : TypeToken<MutableList<Album>>() {}.type
            val albums = Gson().fromJson<MutableList<Album>>(gson, type)

            if (albums != null) {
                topAlbumsAdapter.setData(albums)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("TopAlbumsFragment", "[onDataCancelled] ${error.message}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopAlbumsBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init
        topAlbumsAdapter = TopAlbumsAdapter()
        databaseTopAlbums = FirebaseDatabase.getInstance().getReference("top_albums")

        swipeTopAlbums()
        onClick()

        showLoading()
        showTopAlbums()
    }

    private fun onClick() {
        topAlbumsAdapter.onClick { album ->
            context?.startActivity<DetailAlbumActivity>(DetailAlbumActivity.KEY_ALBUM to album)
        }
    }

    private fun swipeTopAlbums() {
        topAlbumsBinding?.swipeTopAlbums?.setOnRefreshListener {
            showTopAlbums()
        }
    }

    private fun showLoading() {
        topAlbumsBinding?.swipeTopAlbums?.visible()
    }

    private fun hideLoading() {
        topAlbumsBinding?.swipeTopAlbums?.hide()
    }

    private fun showTopAlbums() {
        // GetData
//        val topAlbums = Repository.getDataTopAlbumsFromAssets(context)

        // Data from Firebase
        databaseTopAlbums.addValueEventListener(eventListenerTopAlbums)

        // SetupRecyclerView
        topAlbumsBinding?.rvTopAlbums?.adapter = topAlbumsAdapter
    }
}