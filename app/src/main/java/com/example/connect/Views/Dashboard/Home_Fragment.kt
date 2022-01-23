package com.example.connect.Views.Dashboard

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.CreateStory
import com.example.connect.Dashboard.Companion.token
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.OthersProfile
import com.example.connect.R
import com.example.connect.Repository.CreateStoryRepo
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.HomeStoryRepo
import com.example.connect.Repository.Response
import com.example.connect.ShowStory
import com.example.connect.View_model.*
import com.example.connect.databinding.HomeFragmentBinding
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.HomeStoryDataClass
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import com.example.connect.recylcer_view_adapter.HomeStoryAdapter
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList

class Home_Fragment :Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeStoryViewModel: HomeStoryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private var adapter= HomePageAdapter()
    private lateinit var adapter2:HomeStoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView= binding.postRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : HomePageAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, OthersProfile::class.java)
                intent.putExtra("USER", adapter.Posts[position].user.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].user.toString())
                startActivity(intent)
            }
        })
        recyclerView2=binding.homeStoryrecyclerView
        adapter2=HomeStoryAdapter(requireContext())
        recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.adapter = adapter2
        adapter2.setOnItemClickListener(object : HomeStoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context,ShowStory ::class.java)
                intent.putExtra("USER", adapter2.Posts[position].user.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].user.toString())
                startActivity(intent)
            }
        })

        binding.pickImages.setOnClickListener {
            val intent = Intent(context, CreateStory::class.java)
            startActivity(intent)

        }
        binding.messenger.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_home_Fragment_to_post_Fragment)

        }

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

        val homestoryRepo = HomeStoryRepo(ServiceBuilder1.buildService(token))
        val homeStoryViewModelFactory = HomeStoryViewModelFactory(homestoryRepo)
        homeStoryViewModel = ViewModelProvider(this, homeStoryViewModelFactory)[HomeStoryViewModel::class.java]
        homeStoryViewModel.HomeStory()

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

        homeStoryViewModel.homeStoryResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG)
                        .show()
                    adapter2.setUpdatedData(it.data as ArrayList<HomeStoryDataClass>)
                    binding.userImage.setStrokeColorResource(R.color.gray)
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