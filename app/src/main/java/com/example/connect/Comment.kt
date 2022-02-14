package com.example.connect

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Password_check.Response
import com.example.connect.View_model.CreateCommentViewModel
import com.example.connect.View_model.ShowCommentViewModel
import com.example.connect.databinding.ActivityCommentBinding
import com.example.connect.model.CommentDataClass
import com.example.connect.recylcer_view_adapter.CreateCommentAdapter

class Comment : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private  val createCommentViewModel: CreateCommentViewModel by viewModels()
    private  val showCommentViewModel: ShowCommentViewModel by viewModels()
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


        postid = intent.getStringExtra("USER")?.toInt()

        showCommentViewModel.PostId.setValue(postid)
        showCommentViewModel.ShowCommentSubmitData(this)
        showCommentViewModel.showCommentResult.observe(this, {
            when (it) {
                is Response.Success ->{

                    adapter.setUpdatedData(it.data as ArrayList<CommentDataClass>)
                }
                is Response.Error -> {
                    Toast.makeText(
                        this,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        })
        createCommentViewModel.createCommentResult.observe(this, {
            when (it) {
                is Response.Success ->{ Toast.makeText(this, "Your comment is sent", Toast.LENGTH_LONG)
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
//                is Response.Loading -> {
//                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
//                        .show()
//                }

            }
        })
        binding.PostBtn.setOnClickListener {
            val comment=binding.CommentEdit.text.toString().trim()
            createCommentViewModel.Content.setValue(comment)
            createCommentViewModel.ParentId.setValue(null)
            createCommentViewModel.PostId.setValue(postid)

            createCommentViewModel.CreateCommentSubmitData(this)
            Toast.makeText(this, "Your comment is sent", Toast.LENGTH_LONG)
                .show()
            binding.CommentEdit.text!!.clear()
//            showCommentViewModel.ShowCommentSubmitData(this)
//                        showCommentViewModel.showCommentResult.observe(this, {
//                            when (it) {
//                                is Response.Success ->{
//                                    Toast.makeText(this, "Your comment is sent", Toast.LENGTH_LONG)
//                                        .show()
//                                    adapter.setUpdatedData(it.data as ArrayList<CommentDataClass>)
//                                }
//                                is Response.Error -> {
//                                    Toast.makeText(
//                                        this,
//                                        it.errorMessage,
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//
//
//                            }
//                        })
//            createCommentViewModel.createCommentResult.observe(this, {
//                when (it) {
//                    is Response.Success ->{ Toast.makeText(this, "Your comment is sent", Toast.LENGTH_LONG)
//                        .show()
//
//                        //    adapter.setUpdatedData(it.data)
////                        showCommentViewModel.ShowCommentSubmitData(this)
////                        adapter.notifyDataSetChanged()
////                        showCommentViewModel.showCommentResult.observe(this, {
////                            when (it) {
////                                is Response.Success ->{
////
////                                    adapter.setUpdatedData(it.data as ArrayList<CommentDataClass>)
////                                }
////                                is Response.Error -> {
////                                    Toast.makeText(
////                                        this,
////                                        it.errorMessage,
////                                        Toast.LENGTH_LONG
////                                    ).show()
////                                }
////
////
////                            }
////                        })
////                        binding.CommentEdit.text!!.clear()
//
//                    }
//                    is Response.Error -> {
//                        Toast.makeText(
//                            this,
//                            it.errorMessage,
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
////                is Response.Loading -> {
////                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
////                        .show()
////                }
//
//                }
//            })
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
                    createCommentViewModel.CreateCommentSubmitData(this@Comment)
                    Toast.makeText(this@Comment, "Your comment is sent", Toast.LENGTH_LONG)
                        .show()
                    binding.CommentEdit.text!!.clear()
//                    createCommentViewModel.createCommentResult.observe(this@Comment, {
//                        when (it) {
//                            is Response.Success ->{ Toast.makeText(this@Comment, "Your comment is sent", Toast.LENGTH_LONG)
//                                .show()
//
//                                //    adapter.setUpdatedData(it.data)
////                                showCommentViewModel.ShowCommentSubmitData(this@Comment)
////                                showCommentViewModel.showCommentResult.observe(this@Comment, {
////                                    when (it) {
////                                        is Response.Success ->{
////
////                                            adapter.setUpdatedData(it.data as ArrayList<CommentDataClass>)
////                                        }
////                                        is Response.Error -> {
////                                            Toast.makeText(
////                                                this@Comment,
////                                                it.errorMessage,
////                                                Toast.LENGTH_LONG
////                                            ).show()
////                                        }
////
////
////                                    }
////                                })
//                                binding.CommentEdit.text!!.clear()
//                            }
//                            is Response.Error -> {
//                                Toast.makeText(
//                                    this@Comment,
//                                    it.errorMessage,
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            }
////                is Response.Loading -> {
////                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
////                        .show()
////                }
//
//                        }
//                    })
                }

            }
        })

    }
}