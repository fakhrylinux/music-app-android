package me.fakhry.musicapp.views.mytracks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import me.fakhry.musicapp.adapter.MyTracksAdapter
import me.fakhry.musicapp.databinding.FragmentMyTracksBinding
import me.fakhry.musicapp.models.Song
import me.fakhry.musicapp.utils.gone
import me.fakhry.musicapp.utils.hide
import me.fakhry.musicapp.utils.visible
import me.fakhry.musicapp.views.playsong.PlaySongActivity
import org.jetbrains.anko.startActivity

class MyTracksFragment : Fragment() {
    private var _binding: FragmentMyTracksBinding? = null
    private val myTracksBinding get() = _binding
    private lateinit var myTracksAdapter: MyTracksAdapter
    private var currentUser: FirebaseUser? = null
    private lateinit var databaseMyTracks: DatabaseReference

    private val eventListenerMyTracks = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val songs = mutableListOf<Song>()

            if (snapshot.value != null) {
                hideEmptyData()
                for (snap in snapshot.children) {
                    val song = snap.getValue(Song::class.java)
                    if (song != null) {
                        songs.add(song)
                    }
                }

                myTracksAdapter.setData(songs)
            } else {
                showEmptyData()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            Log.e("MyTracksFragment", "[onCancelled] ${error.message}")
        }

    }

    private fun hideEmptyData() {
        myTracksBinding?.ivEmptyData?.gone()
        myTracksBinding?.rvMyTracks?.visible()
    }

    private fun showEmptyData() {
        myTracksBinding?.ivEmptyData?.visible()
        myTracksBinding?.rvMyTracks?.gone()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyTracksBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Init
        myTracksAdapter = MyTracksAdapter()
        currentUser = FirebaseAuth.getInstance().currentUser
        databaseMyTracks = FirebaseDatabase.getInstance().getReference("users")

        swipeMyTracks()
        onClick()

        showLoading()
        showMyTracks()
    }

    private fun onClick() {
        myTracksAdapter.onClick { songs, position ->
            context?.startActivity<PlaySongActivity>(
                PlaySongActivity.KEY_SONGS to songs,
                PlaySongActivity.KEY_POSITION to position
            )
        }
    }

    private fun swipeMyTracks() {
        myTracksBinding?.swipeMyTracks?.setOnRefreshListener {
            showMyTracks()
        }
    }

    private fun showLoading() {
        myTracksBinding?.swipeMyTracks?.visible()
    }

    private fun hideLoading() {
        myTracksBinding?.swipeMyTracks?.hide()
    }

    private fun showMyTracks() {
        hideLoading()
        // GetData
//        val topCharts = Repository.getDataTopChartsFromAssets(context)

        // Get data from Firebase
        databaseMyTracks
            .child(currentUser?.uid.toString())
            .child("my_tracks")
            .addValueEventListener(eventListenerMyTracks)

        // SetupRecyclerView
        myTracksBinding?.rvMyTracks?.adapter = myTracksAdapter
    }
}