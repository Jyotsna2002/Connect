package com.example.connect.Views.Dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.connect.databinding.PostFragmentBinding

class Post_Fragment : Fragment() {
    private var _binding: PostFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}