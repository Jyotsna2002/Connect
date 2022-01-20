package com.example.connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.SeeTagPostRepo
import com.example.connect.View_model.*
import com.example.connect.databinding.ActivityPostBinding
import com.example.connect.databinding.PostBinding
import com.example.connect.databinding.ProfileFragmentBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.OthersProfileAdapter
import com.example.connect.recylcer_view_adapter.SeeTagAdapter

class Post : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private lateinit var seeTagViewModel: SeeTagViewModel
    var Tag: String?=null
    private lateinit var recyclerView: RecyclerView
    private var adapter= SeeTagAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        recyclerView= binding.seePost
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        Tag = intent.getStringExtra("USER")
        Log.i("tag", "access:$Tag")
        val seeTagRepo = SeeTagPostRepo( ServiceBuilder1.buildService(Dashboard.token))
        val seeTagViewModelFactory = SeeTagViewModelFactory(seeTagRepo)
        seeTagViewModel = ViewModelProvider(this, seeTagViewModelFactory)[SeeTagViewModel::class.java]

        seeTagViewModel.Tag.setValue(Tag)
        seeTagViewModel.submitTagPost()
        seeTagViewModel.seePostResult.observe(this, {
            when (it) {
                is Response.Success ->{ Toast.makeText(this, "Success", Toast.LENGTH_LONG)
                    .show()

                    adapter.setUpdatedData(it.data as ArrayList<OthersPost>)
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
    }
}