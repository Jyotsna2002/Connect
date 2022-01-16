package com.example.connect.Views.Dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Dashboard.Companion.token
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.Response
import com.example.connect.View_model.HomePageViewModelFactory
import com.example.connect.View_model.HomeViewModel
import com.example.connect.databinding.HomeFragmentBinding
import com.example.connect.model.HomeDataClassItem
import com.example.connect.recylcer_view_adapter.HomePageAdapter

class Home_Fragment :Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter= HomePageAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView= binding.postRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        adapter =HomePageAdapter()
        recyclerView.adapter = adapter

        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = android.app.AlertDialog.Builder(activity)
                builder.setTitle("Exit")
                    .setMessage("Are you sure you want to Exit?")
                    .setPositiveButton("Exit") { _, _ ->
                        activity?.finish()
                    }
                    .setNeutralButton("Cancel") { _, _ -> }
                val exit = builder.create()
                exit.show()
            }
        })
        val postshowRepo = HomePageRepo(context, ServiceBuilder1.buildService(token))
        Log.i("token", "access:$token")
        val homeViewModelFactory = HomePageViewModelFactory(postshowRepo)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        homeViewModel.submitPost()

//        adapter = HomePageAdapter(activity, mutableListOf())
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager =
//            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.showpostResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG)
                        .show()
                    adapter.setUpdatedData(it.data as ArrayList<HomeDataClassItem>)
                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Response.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG)
                        .show()
                }

            }
        })
    }
}