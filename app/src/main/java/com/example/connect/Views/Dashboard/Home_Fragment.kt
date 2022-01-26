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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.*
import com.example.connect.Dashboard.Companion.token
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.*
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
    private lateinit var likeStoryViewModel: LikeStoryViewModel
    private lateinit var createBookmarkViewModel: CreateBookmarkViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: HomePageAdapter
    private lateinit var adapter2:HomeStoryAdapter
    var PostId:Int?=null
    var Post:Int?=null
companion object{
    lateinit var Text5:TextView
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        Text5=binding.textView14
        recyclerView= binding.postRecyclerView
        adapter= activity?.let { HomePageAdapter(it) }!!
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
        adapter.setOnItemClickListener2(object : HomePageAdapter.onItemClickListener2 {
            override fun onItemClick2(position: Int) {
                PostId=adapter.Posts[position].post_id
                likeStoryViewModel.PostId.setValue(PostId)
                likeStoryViewModel.LikeStorySubmitData()
                likeStoryViewModel.likePostStoryResult.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {



                            homeViewModel.submitPost()
                        }
                        is Response.Error -> {
                            Toast.makeText(
                                context,
                                it.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    }
                })

            }
        })

        adapter.setOnItemClickListener3(object : HomePageAdapter.onItemClickListener3 {
            override fun onItemClick3(position: Int) {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT,adapter.Posts[position].post_image?.get(0)?.images)
                shareIntent.type = "text/*"
                startActivity(Intent.createChooser(shareIntent, "share movie to"))
            }
        })

        adapter.setOnItemClickListener4(object : HomePageAdapter.onItemClickListener4 {
            override fun onItemClick4(position: Int) {
                val intent = Intent(context, Comment::class.java)
                intent.putExtra("USER", adapter.Posts[position].post_id.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].post_id.toString())
                startActivity(intent)
            }
        })
        adapter.setOnItemClickListener5(object : HomePageAdapter.onItemClickListener5 {
            override fun onItemClick5(position: Int) {
                Post=adapter.Posts[position].post_id
                createBookmarkViewModel.PostId.setValue(Post)
                createBookmarkViewModel.CreateBookmarkSubmitData()
                createBookmarkViewModel.createBookmarkResult.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {
                            homeViewModel.submitPost()
                        }
                        is Response.Error -> {
                            Toast.makeText(
                                context,
                                it.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                })
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
                Log.i("userId", "onActivityResult:" +adapter2.Posts[position].user.toString())
                startActivity(intent)




            }
        })

        binding.pickImages.setOnClickListener {
            val intent = Intent(context, CreateStory::class.java)
            startActivity(intent)

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
        val postshowRepo = HomePageRepo(ServiceBuilder1.buildService(token))
        Log.i("token", "access:$token")
        val homeViewModelFactory = HomePageViewModelFactory(postshowRepo)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        homeViewModel.submitPost()

        val homestoryRepo = HomeStoryRepo(ServiceBuilder1.buildService(token))
        val homeStoryViewModelFactory = HomeStoryViewModelFactory(homestoryRepo)
        homeStoryViewModel = ViewModelProvider(this, homeStoryViewModelFactory)[HomeStoryViewModel::class.java]
        homeStoryViewModel.HomeStory()

        val likestoryRepo = LikePostRepo(ServiceBuilder1.buildService(token))
        val likeStoryViewModelFactory = LikeStoryViewModelFactory(likestoryRepo)
        likeStoryViewModel = ViewModelProvider(this, likeStoryViewModelFactory)[LikeStoryViewModel::class.java]

        val createBookmarkRepo = CreateBookmarkRepo(ServiceBuilder1.buildService(token))
        val createBookmarkViewModelFactory = CreateBookmarkViewModelFactory(createBookmarkRepo)
        createBookmarkViewModel = ViewModelProvider(this, createBookmarkViewModelFactory)[CreateBookmarkViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.showpostResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success -> {

                    adapter.setUpdatedData(it.data as ArrayList<HomeDataClassItem>)


                }
                is Response.Error -> {
//                    Toast.makeText(
//                        context,
//                        it.errorMessage,
//                        Toast.LENGTH_LONG
//                    ).show()
                }


            }
        })

        homeStoryViewModel.homeStoryResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success -> {

                    adapter2.setUpdatedData(it.data as ArrayList<HomeStoryDataClass>)
                   // binding.userImage.setStrokeColorResource(R.color.gray)
                }
                is Response.Error -> {
//                    Toast.makeText(
//                        context,
//                        it.errorMessage,
//                        Toast.LENGTH_LONG
//                    ).show()
                }


            }
        })

    }
}