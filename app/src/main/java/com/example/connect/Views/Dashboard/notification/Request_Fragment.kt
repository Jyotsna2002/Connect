package com.example.connect.Views.Dashboard.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.connect.databinding.AccountsBinding
import com.example.connect.databinding.RequestBinding
import com.example.connect.databinding.SearchFragmentBinding

class Request_Fragment: Fragment() {
    private var _binding: RequestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RequestBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}