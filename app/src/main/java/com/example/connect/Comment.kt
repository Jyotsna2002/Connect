package com.example.connect

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.CreateCommentRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.ShowCommentRepo
import com.example.connect.View_model.CreateCommentViewModel
import com.example.connect.View_model.CreateCommentViewModelFactory
import com.example.connect.View_model.ShowCommentViewModel
import com.example.connect.View_model.ShowCommentViewModelFactory
import com.example.connect.databinding.ActivityCommentBinding
import com.example.connect.model.CommentDataClass
import com.example.connect.recylcer_view_adapter.CreateCommentAdapter
import com.example.connect.recylcer_view_adapter.HomePageAdapter

class Comment : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var createCommentViewModel: CreateCommentViewModel
    private lateinit var showCommentViewModel: ShowCommentViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter= CreateCommentAdapter()
    var postid: Int?=null
    var parentId:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        recyclerView= binding.recyclerView3
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter=adapter
        val createCommentRepo = CreateCommentRepo(ServiceBuilder1.buildService(Dashboard.token))
        val createCommentViewModelFactory = CreateCommentViewModelFactory(createCommentRepo)
        createCommentViewModel = ViewModelProvider(this, createCommentViewModelFactory)[CreateCommentViewModel::class.java]

        val showCommentRepo = ShowCommentRepo(ServiceBuilder1.buildService(Dashboard.token))
        val showCommentViewModelFactory = ShowCommentViewModelFactory(showCommentRepo)
        showCommentViewModel = ViewModelProvider(this, showCommentViewModelFactory)[ShowCommentViewModel::class.java]

        postid = intent.getStringExtra("USER")?.toInt()

        showCommentViewModel.PostId.setValue(postid)
        showCommentViewModel.ShowCommentSubmitData()
        showCommentViewModel.showCommentResult.observe(this, {
            when (it) {
                is Response.Success ->{ Toast.makeText(this, "Success", Toast.LENGTH_LONG)
                    .show()

                    adapter.setUpdatedData(it.data as ArrayList<CommentDataClass>)
                }
                is Response.Error -> {
                    Toast.makeText(
                        this,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Response.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
                        .show()
                }

            }
        })
        createCommentViewModel.createCommentResult.observe(this, {
            when (it) {
                is Response.Success ->{ Toast.makeText(this, "Success", Toast.LENGTH_LONG)
                    .show()

                    //    adapter.setUpdatedData(it.data)
                }
                is Response.Error -> {
                    Toast.makeText(
                        this,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Response.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
                        .show()
                }

            }
        })
        binding.PostBtn.setOnClickListener {
            val comment=binding.CommentEdit.text.toString().trim()
            createCommentViewModel.Content.setValue(comment)
            createCommentViewModel.ParentId.setValue(null)
            createCommentViewModel.PostId.setValue(postid)

            createCommentViewModel.CreateCommentSubmitData()

        }
        adapter.setOnItemClickListener(object : CreateCommentAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                binding.CommentEdit.requestFocus()
                binding.CommentEdit.setFocusableInTouchMode(true)

                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.CommentEdit, InputMethodManager.SHOW_FORCED)
                parentId=adapter.Posts[position].id

                binding.PostBtn.setOnClickListener {
                    val comment=binding.CommentEdit.text.toString().trim()
                    createCommentViewModel.Content.setValue(comment)
                    createCommentViewModel.ParentId.setValue(parentId)
                    Log.i("ParentId", "Respone "+parentId)
                    createCommentViewModel.PostId.setValue(postid)
                    createCommentViewModel.CreateCommentSubmitData()
                }

            }
        })

    }
}