package com.example.connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.ShowStoryRepo
import com.example.connect.View_model.*
import com.example.connect.databinding.ActivityCreateStoryBinding
import com.example.connect.databinding.ActivityShowStoryBinding
import com.example.connect.model.OthersPost
import com.example.connect.model.ShowStoryDataClass

class ShowStory : AppCompatActivity() {
    private lateinit var binding: ActivityShowStoryBinding
    private lateinit var showStoryViewModel: ShowStoryViewModel
    var userid: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()
        val showStoryRepo = ShowStoryRepo( ServiceBuilder1.buildService(Dashboard.token))
        val showStoryViewModelFactory = ShowStoryViewModelFactory(showStoryRepo)
        showStoryViewModel = ViewModelProvider(this, showStoryViewModelFactory)[ShowStoryViewModel::class.java]

        userid = intent.getStringExtra("USER")?.toInt()
        showStoryViewModel.UserId.setValue(userid)
        showStoryViewModel.ShowStorySubmitData()

        showStoryViewModel.showStoryResult.observe(this, {
            when (it) {
                is Response.Success ->{ Toast.makeText(this, "Success", Toast.LENGTH_LONG)
                    .show()

                        for (image in it.data?.listIterator(0)!!){
                            for (images in image.post_image!!)
                            {
                                imageList.add(SlideModel(images.images, ScaleTypes.CENTER_CROP))
                            }
                            imageSlider.setImageList(imageList)
                        }

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