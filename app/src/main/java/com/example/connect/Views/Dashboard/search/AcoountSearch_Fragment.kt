package com.example.connect.Views.Dashboard.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.connect.databinding.AccountsBinding
import com.example.connect.databinding.SearchFragmentBinding

class AcoountSearch_Fragment: Fragment() {
    private var _binding: AccountsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AccountsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}