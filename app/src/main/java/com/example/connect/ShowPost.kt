package com.example.connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Bookmark.Companion.Text8
import com.example.connect.Dashboard.Companion.user
import com.example.connect.Password_check.Response
import com.example.connect.View_model.CreateBookmarkViewModel
import com.example.connect.View_model.LikeStoryViewModel
import com.example.connect.View_model.OthersProfilePostViewModel
import com.example.connect.databinding.ActivityPostBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.SeeTagAdapter

class ShowPost : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private val likeStoryViewModel: LikeStoryViewModel by viewModels()
    private  val createBookmarkViewModel: CreateBookmarkViewModel by viewModels()
    private  val othersprofilepostViewModel: OthersProfilePostViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private var adapter= SeeTagAdapter()
    var PostId:Int?=null
    var Post:Int?=null
    var userid:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Text8 =binding.book
        recyclerView= binding.seePost
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        userid = intent.getStringExtra("USER")?.toInt()
        adapter.setOnItemClickListener(object : SeeTagAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@ShowPost, OthersProfile::class.java)
                intent.putExtra("USER", adapter.Posts[position].user.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].user.toString())
                startActivity(intent)
            }
        })
        adapter.setOnItemClickListener2(object : SeeTagAdapter.onItemClickListener2 {
            override fun onItemClick2(position: Int) {
                PostId=adapter.Posts[position].post_id
                likeStoryViewModel.PostId.setValue(PostId)
                likeStoryViewModel.LikeStorySubmitData(this@ShowPost)
                likeStoryViewModel.likePostStoryResult.observe(this@ShowPost, {
                    when (it) {
                        is Response.Success -> {

                            othersprofilepostViewModel.submitotherprofilepost(this@ShowPost)
                            othersprofilepostViewModel.showotherProfilPostResult.observe(this@ShowPost, {
                                when (it) {
                                    is Response.Success ->{

                                        adapter.setUpdatedData(it.data as ArrayList<OthersPost>)

                                    }
                                    is Response.Error -> {
                                        Toast.makeText(
                                            this@ShowPost,
                                            it.errorMessage,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }


                                }
                            })
                        }
                        is Response.Error -> {
                            Toast.makeText(
                                this@ShowPost,
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
                val intent = Intent(this@ShowPost, Comment::class.java)
                intent.putExtra("USER", adapter.Posts[position].post_id.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].post_id.toString())
                startActivity(intent)
            }
        })
        adapter.setOnItemClickListener5(object : SeeTagAdapter.onItemClickListener5 {
            override fun onItemClick5(position: Int) {
                Post=adapter.Posts[position].post_id
                createBookmarkViewModel.PostId.setValue(Post)
                createBookmarkViewModel.CreateBookmarkSubmitData(this@ShowPost)
                createBookmarkViewModel.createBookmarkResult.observe(this@ShowPost, {
                    when (it) {
                        is Response.Success -> {
                            othersprofilepostViewModel.submitotherprofilepost(this@ShowPost)
                            othersprofilepostViewModel.showotherProfilPostResult.observe(this@ShowPost, {
                                when (it) {
                                    is Response.Success ->{

                                        adapter.setUpdatedData(it.data as ArrayList<OthersPost>)

                                    }
                                    is Response.Error -> {
                                        Toast.makeText(
                                            this@ShowPost,
                                            it.errorMessage,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }


                                }
                            })
                        }
                        is Response.Error -> {
                            Toast.makeText(
                                this@ShowPost,
                                it.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                })
            }
        })


        if(userid==user.toInt())
        {
            othersprofilepostViewModel.User_id.setValue(user.toInt())
        }
        else{
            othersprofilepostViewModel.User_id.setValue(userid)
        }

        othersprofilepostViewModel.submitotherprofilepost(this)
        othersprofilepostViewModel.showotherProfilPostResult.observe(this, {
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