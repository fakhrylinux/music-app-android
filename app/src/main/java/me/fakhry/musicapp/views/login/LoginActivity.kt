package me.fakhry.musicapp.views.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import me.fakhry.musicapp.R
import me.fakhry.musicapp.databinding.ActivityLoginBinding
import me.fakhry.musicapp.utils.gone
import me.fakhry.musicapp.utils.visible
import me.fakhry.musicapp.views.forgotpassword.ForgotPasswordActivity
import me.fakhry.musicapp.views.main.MainActivity
import me.fakhry.musicapp.views.register.RegisterActivity
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var bd: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        bd = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bd.root)

        // Init Firebase auth
        auth = FirebaseAuth.getInstance()
        checkIfAlreadyLogin()

        onClick()
    }

    private fun checkIfAlreadyLogin() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity<MainActivity>()
            finishAffinity()
        }
    }

    private fun onClick() {
        bd.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        bd.btnNewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        bd.btnLogin.setOnClickListener {
            val email = bd.etEmail.text.toString().trim()
            val pass = bd.etPassword.text.toString().trim()

            if (checkValidation(email, pass)) {
                loginToServer(email, pass)
            }
        }
    }

    private fun loginToServer(email: String, pass: String) {
        showLoading()
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                hideLoading()
                startActivity<MainActivity>()
                finishAffinity()
            }
            .addOnFailureListener {
                hideLoading()
                AlertDialog.Builder(this).setTitle(getString(R.string.error)).setMessage(it.message)
                    .show()
            }
    }

    private fun hideLoading() {
        bd.pbLogin.gone()
    }

    private fun showLoading() {
        bd.pbLogin.visible()
    }

    private fun checkValidation(email: String, pass: String): Boolean {
        if (email.isEmpty()) {
            bd.etEmail.error = getString(R.string.please_field_your_email)
            bd.etEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            bd.etEmail.error = getString(R.string.please_use_valid_email)
            bd.etEmail.requestFocus()
        } else if (pass.isEmpty()) {
            bd.etPassword.error = "Please field your password"
            bd.etPassword.requestFocus()
        } else {
            return true
        }
        return false
    }
}