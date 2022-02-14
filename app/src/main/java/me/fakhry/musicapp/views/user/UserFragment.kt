package me.fakhry.musicapp.views.user

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.fakhry.musicapp.databinding.FragmentUserBinding
import me.fakhry.musicapp.views.changepassword.ChangePasswordActivity
import me.fakhry.musicapp.views.edituser.EditUserActivity
import me.fakhry.musicapp.views.main.MainActivity

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val userBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    private fun onClick() {
        userBinding?.btnEditUser?.setOnClickListener {
            context?.startActivity(Intent(context, EditUserActivity::class.java))
        }

        userBinding?.btnChangeLanguage?.setOnClickListener {
            startActivity(Intent(ACTION_LOCALE_SETTINGS))
        }

        userBinding?.btnChangePassword?.setOnClickListener {
            context?.startActivity(Intent(context, ChangePasswordActivity::class.java))
        }

        userBinding?.btnLogout?.setOnClickListener {
            (activity as MainActivity).finish()
        }
    }
}