package com.example.connect.Views.Dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Dashboard.Companion.token
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.OthersProfile
import com.example.connect.R
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
    private var images:ArrayList<Uri?>?=null
    private val PICK_IMAGE_CODE=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        images= ArrayList()
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
        binding.messenger.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_home_Fragment_to_post_Fragment)
        }
        binding.pickImages.setOnClickListener {
            pickImages()
        }
        return view
    }


    private fun pickImages(){
        val intent=Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Image(s)"),PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==PICK_IMAGE_CODE){
            if(resultCode==Activity.RESULT_OK){

                if (data!!.clipData != null){

                    val count=data.clipData!!.itemCount
                    for(i in 0 until count)
                    {
                        val imageUri=data.clipData!!.getItemAt(i).uri
                        images!!.add(imageUri)
                    }
                }
                else{
                    val imageUri=data.data
                }
            }
        }
       // Toast.makeText(this, images, Toast.LENGTH_SHORT).show()
        Log.i("Images", "onActivityResult: ${images.toString()}")
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