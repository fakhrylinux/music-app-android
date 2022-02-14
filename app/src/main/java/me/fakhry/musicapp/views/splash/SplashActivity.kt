package me.fakhry.musicapp.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.musicapp.databinding.ActivitySplashBinding
import me.fakhry.musicapp.views.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBd: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBd = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBd.root)

        delayAndToLogin()
    }

    private fun delayAndToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }, 1200)
    }
}