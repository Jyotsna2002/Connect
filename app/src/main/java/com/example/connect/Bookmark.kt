package com.example.connect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.View_model.*
import com.example.connect.Views.Dashboard.Profile_Fragment
import com.example.connect.databinding.ActivityPostBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.SeeTagAdapter

class Bookmark : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private val likeStoryViewModel: LikeStoryViewModel by viewModels()
    private  val createBookmarkViewModel: CreateBookmarkViewModel by viewModels()
    private  val showBookmarkViewModel: ShowBookmarkViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private var adapter= SeeTagAdapter()
    var PostId:Int?=null
    var Post:Int?=null
    companion object{
        lateinit var Text8: TextView
    }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityPostBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
            Text8 =binding.book
            recyclerView= binding.seePost
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : SeeTagAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@Bookmark, OthersProfile::class.java)
                    intent.putExtra("USER", adapter.Posts[position].user.toString())
                    Log.i("userId", "onActivityResult:" +adapter.Posts[position].user.toString())
                    startActivity(intent)
                }
            })
            adapter.setOnItemClickListener2(object : SeeTagAdapter.onItemClickListener2 {
                override fun onItemClick2(position: Int) {
                    PostId=adapter.Posts[position].post_id
                    likeStoryViewModel.PostId.setValue(PostId)
                    likeStoryViewModel.LikeStorySubmitData(this@Bookmark)
                    likeStoryViewModel.likePostStoryResult.observe(this@Bookmark, {
                        when (it) {
                            is Response.Success -> {

                                showBookmarkViewModel.ShowBookmarkSubmitData(this@Bookmark)
                                showBookmarkViewModel.showBookmarkResult.observe(this@Bookmark, {
                                    when (it) {
                                        is Response.Success ->{

                                            adapter.setUpdatedData(it.data as ArrayList<OthersPost>)
                                        }
                                        is Response.Error -> {
                                            Toast.makeText(
                                                this@Bookmark,
                                                it.errorMessage,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }


                                    }
                                })
                            }
                            is Response.Error -> {
                                Toast.makeText(
                                    this@Bookmark,
                                    it.errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }


                        }
                    })

                }
            })

            adapter.setOnItemClickListener3(object : SeeTagAdapter.onItemClickListener3 {
                override fun onItemClick3(position: Int) {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(Intent.EXTRA_TEXT,adapter.Posts[position].post_image?.get(0)?.images)
                    shareIntent.type = "text/*"
                    startActivity(Intent.createChooser(shareIntent, "share movie to"))
                }
            })

            adapter.setOnItemClickListener4(object : SeeTagAdapter.onItemClickListener4 {
                override fun onItemClick4(position: Int) {
                    val intent = Intent(this@Bookmark, Comment::class.java)
                    intent.putExtra("USER", adapter.Posts[position].post_id.toString())
                    Log.i("userId", "onActivityResult:" +adapter.Posts[position].post_id.toString())
                    startActivity(intent)
                }
            })
            adapter.setOnItemClickListener5(object : SeeTagAdapter.onItemClickListener5 {
                override fun onItemClick5(position: Int) {
                    Post=adapter.Posts[position].post_id
                    createBookmarkViewModel.PostId.setValue(Post)
                    createBookmarkViewModel.CreateBookmarkSubmitData(this@Bookmark)
                    createBookmarkViewModel.createBookmarkResult.observe(this@Bookmark, {
                        when (it) {
                            is Response.Success -> {
                                showBookmarkViewModel.ShowBookmarkSubmitData(this@Bookmark)
                                showBookmarkViewModel.showBookmarkResult.observe(this@Bookmark, {
                                    when (it) {
                                        is Response.Success ->{

                                            adapter.setUpdatedData(it.data as ArrayList<OthersPost>)
                                        }
                                        is Response.Error -> {
                                            Toast.makeText(
                                                this@Bookmark,
                                                it.errorMessage,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }


                                    }
                                })
                                    }
                            is Response.Error -> {
                                Toast.makeText(
                                    this@Bookmark,
                                    it.errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                    })
                }
            })



            showBookmarkViewModel.ShowBookmarkSubmitData(this)
            showBookmarkViewModel.showBookmarkResult.observe(this, {
                when (it) {
                    is Response.Success ->{

                        adapter.setUpdatedData(it.data as ArrayList<OthersPost>)
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
        }
}