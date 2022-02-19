package me.fakhry.musicapp.views.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import me.fakhry.musicapp.R
import me.fakhry.musicapp.databinding.ActivityRegisterBinding
import me.fakhry.musicapp.models.User
import me.fakhry.musicapp.utils.gone
import me.fakhry.musicapp.utils.visible
import me.fakhry.musicapp.views.login.LoginActivity
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var bd: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bd.root)

        init()
        onClick()
    }

    private fun onClick() {
        bd.tbRegister.setNavigationOnClickListener {
            finish()
        }

        bd.btnAlreadyMemberLogin.setOnClickListener {
            finish()
        }

        bd.btnRegister.setOnClickListener {
            val fullName = bd.etFullName.text.toString().trim()
            val email = bd.etEmail.text.toString().trim()
            val pass = bd.etPassword.text.toString().trim()
            val confirmPass = bd.etConfirmPassword.text.toString().trim()

            if (checkValidation(fullName, email, pass, confirmPass)) {
                registerToServer(fullName, email, pass)
            }
        }
    }

    private fun registerToServer(fullName: String, email: String, pass: String) {
        showLoading()
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    val user = User(fullName = fullName, email = email, uid = uid)

                    userDatabase.child(uid.toString()).setValue(user)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                hideLoading()
                                AlertDialog.Builder(this)
                                    .setTitle(getString(R.string.success))
                                    .setMessage(getString(R.string.your_account_has_been_created))
                                    .show()

                                Handler(Looper.getMainLooper()).postDelayed({
                                    startActivity<LoginActivity>()
                                }, 1500)
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
            }
            .addOnFailureListener {
                hideLoading()
                AlertDialog.Builder(this).setTitle(getString(R.string.error)).setMessage(it.message)
                    .show()
            }
    }

    private fun hideLoading() {
        bd.pbRegister.gone()
    }

    private fun showLoading() {
        bd.pbRegister.visible()
    }

    private fun checkValidation(
        fullName: String,
        email: String,
        pass: String,
        confirmPass: String
    ): Boolean {
        if (fullName.isEmpty()) {
            bd.etFullName.error = getString(R.string.please_field_your_full_name)
            bd.etFullName.requestFocus()
        } else if (email.isEmpty()) {
            bd.etEmail.error = getString(R.string.please_field_your_email)
            bd.etEmail.requestFocus()
        } else if (pass.isEmpty()) {
            bd.etPassword.error = getString(R.string.please_field_your_password)
            bd.etPassword.requestFocus()
        } else if (confirmPass.isEmpty()) {
            bd.etConfirmPassword.error = getString(R.string.please_field_your_password)
            bd.etConfirmPassword.requestFocus()
        } else if (pass != confirmPass) {
            bd.etPassword.error = getString(R.string.your_password_did_not_match)
            bd.etPassword.requestFocus()
            bd.etConfirmPassword.error =
                getString(R.string.your_password_did_not_match)
            bd.etConfirmPassword.requestFocus()
        } else {
            return true
        }
        return false
    }

    private fun init() {
        // Setup Support Action Bar
        setSupportActionBar(bd.tbRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        auth = FirebaseAuth.getInstance()
        userDatabase = FirebaseDatabase.getInstance().getReference("users")
    }
}