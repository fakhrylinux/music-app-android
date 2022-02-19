package me.fakhry.musicapp.views.topcharts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.fakhry.musicapp.adapter.TopChartsAdapter
import me.fakhry.musicapp.databinding.FragmentTopChartsBinding
import me.fakhry.musicapp.models.Song
import me.fakhry.musicapp.utils.hide
import me.fakhry.musicapp.utils.visible
import me.fakhry.musicapp.views.playsong.PlaySongActivity
import org.jetbrains.anko.startActivity

class TopChartsFragment : Fragment() {
    private var _binding: FragmentTopChartsBinding? = null
    private val topChartsBinding get() = _binding
    private lateinit var topChartsAdapter: TopChartsAdapter
    private lateinit var databaseTopCharts: DatabaseReference

    private val eventListenerTopCharts = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            Log.d("TopChartsFragment", "[onDataChange] ${snapshot.value}")
            val gson = Gson().toJson(snapshot.value)
            val type = object : TypeToken<MutableList<Song>>() {}.type
            val songs = Gson().fromJson<MutableList<Song>>(gson, type)

            if (songs != null) {
                topChartsAdapter.setData(songs)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            Log.e("TopChartsFragment", "[onCancelled] ${error.message}")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopChartsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Init
        topChartsAdapter = TopChartsAdapter()
        databaseTopCharts = FirebaseDatabase.getInstance().getReference("top_charts")

        swipeTopCharts()
        onClick()

        showLoading()
        showTopCharts()
    }

    private fun onClick() {
        topChartsAdapter.onClick { songs, position ->
            context?.startActivity<PlaySongActivity>(
                PlaySongActivity.KEY_SONGS to songs,
                PlaySongActivity.KEY_POSITION to position
            )
        }
    }

    private fun swipeTopCharts() {
        topChartsBinding?.swipeTopCharts?.setOnRefreshListener {
            showTopCharts()
        }
    }

    private fun showLoading() {
        topChartsBinding?.swipeTopCharts?.visible()
    }

    private fun hideLoading() {
        topChartsBinding?.swipeTopCharts?.hide()
    }

    private fun showTopCharts() {
        // GetData
//        val topCharts = Repository.getDataTopChartsFromAssets(context)

        // From Firebase
        databaseTopCharts.addValueEventListener(eventListenerTopCharts)

        // SetupRecyclerView
        topChartsBinding?.rvTopCharts?.adapter = topChartsAdapter
    }
}
