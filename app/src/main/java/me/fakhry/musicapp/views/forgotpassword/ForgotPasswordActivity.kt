package me.fakhry.musicapp.views.forgotpassword

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import me.fakhry.musicapp.R
import me.fakhry.musicapp.databinding.ActivityForgotPasswordBinding
import me.fakhry.musicapp.utils.gone
import me.fakhry.musicapp.utils.visible

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var bd: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(bd.root)

        init()
        onClick()
    }

    private fun onClick() {
        bd.tbForgotPassword.setNavigationOnClickListener {
            finish()
        }

        bd.btnForgotPassword.setOnClickListener {
            val email = bd.etEmail.text.toString().trim()
            if (checkValidation(email)) {
                forgotPasswordToServer(email)
            }
        }
    }

    private fun forgotPasswordToServer(email: String) {
        showLoading()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    hideLoading()
                    val alert = AlertDialog.Builder(this).setTitle(getString(R.string.success))
                        .setMessage(getString(R.string.link_for_reset_password_has_been_sent_to_your_email))
                        .show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alert.dismiss()
                        finish()
                    }, 3000)
                }
            }
            .addOnFailureListener {
                hideLoading()
                AlertDialog.Builder(this).setTitle(getString(R.string.error)).setMessage(it.message)
                    .show()
            }
    }

    private fun showLoading() {
        bd.pbForgotPassword.visible()
    }

    private fun hideLoading() {
        bd.pbForgotPassword.gone()
    }

    private fun checkValidation(email: String): Boolean {
        if (email.isEmpty()) {
            bd.etEmail.error = getString(R.string.please_field_your_email)
            bd.etEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            bd.etEmail.error = getString(R.string.please_use_valid_email)
            bd.etEmail.requestFocus()
        } else {
            return true
        }
        return false
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(bd.tbForgotPassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        auth = FirebaseAuth.getInstance()
    }
}