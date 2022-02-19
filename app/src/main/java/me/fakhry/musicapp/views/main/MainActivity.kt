package me.fakhry.musicapp.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.fakhry.musicapp.R
import me.fakhry.musicapp.databinding.ActivityMainBinding
import me.fakhry.musicapp.views.mytracks.MyTracksFragment
import me.fakhry.musicapp.views.topalbums.TopAlbumsFragment
import me.fakhry.musicapp.views.topcharts.TopChartsFragment
import me.fakhry.musicapp.views.user.UserFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bd: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val selectedItemId = bd.btmNavigationMain.getSelectedItemId()
        if (selectedItemId == R.id.action_top_charts) {
            finishAffinity()
        } else {
            openHomeFragment()
        }
    }

    private fun init() {
        // Setup BottomNavigation Bar
        bd.btmNavigationMain.setOnItemSelectedListener { id ->
            when (id) {
                R.id.action_top_charts -> openFragment(TopChartsFragment())
                R.id.action_my_tracks -> openFragment(MyTracksFragment())
                R.id.action_top_albums -> openFragment(TopAlbumsFragment())
                R.id.action_user -> openFragment(UserFragment())
            }
        }
        openHomeFragment()
    }

    private fun openHomeFragment() {
        bd.btmNavigationMain.setItemSelected(R.id.action_top_charts)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}