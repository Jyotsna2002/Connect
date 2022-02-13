package com.example.connect.Views.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.connect.Password_check.Datastore
import com.example.connect.R
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


//        lifecycleScope.launch {
//            datastore = Datastore(requireContext())
//            if (datastore.isLogin()) {
//                activity?.finish()
//                findNavController().navigate(R.id.action_landingPage_Fragment_to_dashboard)
//            }
//        }
        binding.log.setOnClickListener {
            findNavController().navigate(R.id.action_landingPage_Fragment_to_login_Fragment)
        }
        binding.sign.setOnClickListener {
            findNavController().navigate(R.id.action_landingPage_Fragment_to_username_Fragment)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        datastore = this.let { Datastore(requireContext()) }
        lifecycleScope.launch {
            access=  datastore.getUserDetails(Datastore.ACCESS_TOKEN_KEY).toString()
           // datastore.changeLoginState(false)
        }
    }
    }