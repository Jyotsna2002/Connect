package com.example.connect.Views.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.connect.Dashboard
import com.example.connect.R
import com.example.connect.Repository.Datastore
import com.example.connect.databinding.LandingPageFragmentBinding
import kotlinx.coroutines.launch


class LandingPage_Fragment: Fragment() {
    companion object{
        lateinit var forget:String
        var access:String?=null
    }
    private var _binding: LandingPageFragmentBinding?=null
    private val binding get() = _binding!!
    lateinit var datastore: Datastore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LandingPageFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        forget="false"

        binding.log.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_landingPage_Fragment_to_login_Fragment)
        }
        binding.sign.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_landingPage_Fragment_to_username_Fragment)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        datastore = this.let { Datastore(context) }
        lifecycleScope.launch {
            access=  datastore.getUserDetails(Datastore.ACCESS_TOKEN_KEY).toString()
        }
    }
    }