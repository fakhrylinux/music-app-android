package me.fakhry.musicapp.views.topalbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.fakhry.musicapp.databinding.FragmentTopAlbumsBinding

class TopAlbumsFragment : Fragment() {
    private var _binding: FragmentTopAlbumsBinding? = null
    private val topAlbumsBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopAlbumsBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}