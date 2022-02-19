package me.fakhry.musicapp.views.changepassword

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import me.fakhry.musicapp.R
import me.fakhry.musicapp.databinding.ActivityChangePasswordBinding
import me.fakhry.musicapp.utils.gone
import me.fakhry.musicapp.utils.visible

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var bd: ActivityChangePasswordBinding
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(bd.root)

        init()
        onClick()
    }

    private fun onClick() {
        bd.tbChangePassword.setNavigationOnClickListener {
            finish()
        }

        bd.btnUpdate.setOnClickListener {
            val oldPass = bd.etOldPassword.text.toString().trim()
            val newPass = bd.etNewPassword.text.toString().trim()

            if (checkValidation(oldPass, newPass)) {
                changePasswordToServer(oldPass, newPass)
            }
        }
    }

    private fun changePasswordToServer(oldPass: String, newPass: String) {
        showLoading()
        val credential = EmailAuthProvider.getCredential(currentUser.email.toString(), oldPass)
        currentUser.reauthenticate(credential)
            .addOnSuccessListener {
                currentUser.updatePassword(newPass)
                    .addOnSuccessListener {
                        hideLoading()
                        AlertDialog.Builder(this)
                            .setTitle(R.string.success)
                            .setMessage(getString(R.string.your_password_has_been_changed))
                            .show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 2000)
                    }
            }
            .addOnFailureListener {
                hideLoading()
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.error))
                    .setMessage(it.message)
                    .show()
            }
    }

    private fun showLoading() {
        bd.pbChangePassword.visible()
    }

    private fun hideLoading() {
        bd.pbChangePassword.gone()
    }

    private fun checkValidation(oldPass: String, newPass: String): Boolean {
        if (oldPass.isEmpty()) {
            bd.etOldPassword.error = getString(R.string.please_field_your_password)
            bd.etOldPassword.requestFocus()
        } else if (newPass.isEmpty()) {
            bd.etNewPassword.error = getString(R.string.please_field_your_password)
            bd.etNewPassword.requestFocus()
        } else {
            return true
        }
        return false
    }

    private fun init() {
        //Set Support ActionBar
        setSupportActionBar(bd.tbChangePassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        currentUser = FirebaseAuth.getInstance().currentUser!!
    }
}