package com.example.connect

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.CreateStoryRepo
import com.example.connect.Repository.Response
import com.example.connect.View_model.CreateStoryViewModel
import com.example.connect.View_model.CreateStoryViewModelFactory
import com.example.connect.Views.Dashboard.Home_Fragment
import com.example.connect.databinding.ActivityCreateStoryBinding
import com.example.connect.databinding.ProfileFragmentBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CreateStory : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStoryBinding
    private lateinit var createStoryViewModel: CreateStoryViewModel
    private var IMAGE_REQUEST_CODE = 100
    private lateinit var ImageUri: ArrayList<String>
    private lateinit var Imageuri: Uri
    companion object{
        var multiple=0
        var empty=0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ImageUri= ArrayList()
        val createstoryRepo = CreateStoryRepo(ServiceBuilder1.buildService(Dashboard.token))
        val createStoryViewModelFactory = CreateStoryViewModelFactory(createstoryRepo)
        createStoryViewModel = ViewModelProvider(this, createStoryViewModelFactory)[CreateStoryViewModel::class.java]

        pickImages()
        binding.uploadstory.setOnClickListener {
            binding.uploadstory.isClickable=false
            if(multiple ==1) {

                createStoryViewModel.submitDataCreateStory(ImageUri)
            }
            else if(multiple ==0) {

                createStoryViewModel.submitDataCreateStory(ImageUri)
            }

            createStoryViewModel.createStoryResult.observe(this, {
                when (it) {
                    is Response.Success ->{ Toast.makeText(this, "Your story is uploaded", Toast.LENGTH_LONG)
                        .show()
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

    private fun pickImages(){
        val intent= Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        empty =1
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data!!.clipData != null) {
                multiple =1
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri

                    val progressDialog = ProgressDialog(this)
                    progressDialog.setMessage("Uploading File...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    val randomKey = UUID.randomUUID().toString()
                    val storageReference =
                        FirebaseStorage.getInstance().getReference("images/" + randomKey)
                    storageReference.putFile(imageUri)
                        .addOnSuccessListener {
                            it.storage.downloadUrl.addOnSuccessListener {
                                binding.postsee.setImageURI(imageUri)
//                                Toast.makeText(this, "successfully Uploaded", Toast.LENGTH_SHORT)
//                                    .show()
                                ImageUri!!.add(it.toString())
                                Log.i(
                                    "HelloUri",
                                    "onActivityResult: ${ImageUri}"
                                )
                                if (progressDialog.isShowing)
                                    progressDialog.dismiss()


                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            if (progressDialog.isShowing)
                                progressDialog.dismiss()
                        }
                }
            }
            else
            {
                multiple =0
                Imageuri = data.getData()!!
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Uploading File...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                val randomKey = UUID.randomUUID().toString()
                val storageReference =
                    FirebaseStorage.getInstance().getReference("images/" + randomKey)
                storageReference.putFile(Imageuri)
                    .addOnSuccessListener {
                        it.storage.downloadUrl.addOnSuccessListener {
                             binding.postsee.setImageURI(Imageuri)
//                            Toast.makeText(this, "successfully Uploaded", Toast.LENGTH_SHORT)
//                                .show()
                            createStoryViewModel.imageUrl.setValue(listOf(it.toString()))
                            if (progressDialog.isShowing)
                                progressDialog.dismiss()

                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        if (progressDialog.isShowing)
                            progressDialog.dismiss()
                    }
            }
        }
    }
}