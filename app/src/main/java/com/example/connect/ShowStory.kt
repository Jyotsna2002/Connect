package com.example.connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Response
import com.example.connect.Repository.ShowStoryRepo
import com.example.connect.View_model.*
import com.example.connect.databinding.ActivityShowStoryBinding

class ShowStory : AppCompatActivity() {
    private lateinit var binding: ActivityShowStoryBinding
    private  val showStoryViewModel: ShowStoryViewModel by viewModels()
    var userid: Int?=null
    var storyid:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        userid = intent.getStringExtra("USER")?.toInt()
        showStoryViewModel.UserId.setValue(userid)
        showStoryViewModel.ShowStorySubmitData(this)

        showStoryViewModel.showStoryResult.observe(this, {
            when (it) {
                is Response.Success ->{

                        for (image in it.data?.listIterator(0)!!){
                          //  storyid=image.id
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


            }
        })

//        binding.button.setOnClickListener {
//
//            deleteStoryViewModel.StoryId.setValue(storyid)
//            deleteStoryViewModel.DeleteStorySubmitData()
//            deleteStoryViewModel.deleteStoryResult.observe(this, {
//                when (it) {
//                    is Response.Success ->{ Toast.makeText(this, "Success", Toast.LENGTH_LONG)
//                        .show()
//
//                    }
//                    is Response.Error -> {
//                        Toast.makeText(
//                            this,
//                            it.errorMessage,
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                    is Response.Loading -> {
//                        Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
//                            .show()
//                    }
//
//                }
//            })
        }
    }
