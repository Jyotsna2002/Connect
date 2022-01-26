package com.example.connect

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.connect.Dashboard.Companion.username
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.EditProfileRepo
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.View_model.EditProfileViewModel
import com.example.connect.View_model.EditProfileViewModelFactory
import com.example.connect.View_model.OthersProfileViewModel
import com.example.connect.View_model.OthersProfileViewModelFactory
import com.example.connect.Views.Dashboard.Post_Fragment
import com.example.connect.Views.Dashboard.Profile_Fragment
import com.example.connect.databinding.EditFragmentBinding
import com.example.connect.databinding.ProfileFragmentBinding
import com.example.connect.model.OthersPost
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.properties.Delegates

class EditProfile : AppCompatActivity() {
    private lateinit var binding: EditFragmentBinding
    private var IMAGE_REQUEST_CODE = 100
    private lateinit var Imageuri: Uri
    private lateinit var profilePhoto:String
    private lateinit var click:String
    private lateinit var editViewModel:EditProfileViewModel
    var value:Boolean?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = EditFragmentBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
        Log.d("checkbox", "onCreate:Hello")
        click=intent.getStringExtra("CLICK").toString()
        profilePhoto= intent.getStringExtra("Photo").toString()
        Log.i("checkbox", "onCreate:"+click)
        if(click=="true")
        {
            Log.i("checkbox", "onCreate:"+value)
            binding.checkBox.isChecked=true
        }
        else
        {
            binding.checkBox.isChecked=false
        }
        binding.SetProfilePhoto.load(profilePhoto) {
            ImageView.ScaleType.CENTER_CROP
            crossfade(true)
            placeholder(R.drawable.ic_baseline_circle_24)
        }
        binding.changeUsernameEditText.setText(username)
        val editProfileRepo = EditProfileRepo( ServiceBuilder1.buildService(Dashboard.token))
        val editViewModelFactory = EditProfileViewModelFactory(editProfileRepo)
        editViewModel = ViewModelProvider(this, editViewModelFactory)[EditProfileViewModel::class.java]

        binding.SaveChanges.setOnClickListener {

            if (binding.checkBox.isChecked==true){
                editViewModel.private.setValue(true)
            }
            else
            {
                editViewModel.private.setValue(false)
            }
            val UserName=binding.changeUsernameEditText.text.toString().trim()
            val Bio=binding.SetYourBioEditText.text.toString().trim()
            editViewModel.Username.setValue(UserName)
            editViewModel.Bio.setValue(Bio)
            editViewModel.EditProfileSubmitData()
            editViewModel.editResult.observe(this, {
                when (it) {
                    is Response.Success ->{ Toast.makeText(this, "Your Profile is updated", Toast.LENGTH_LONG)
                        .show()

//                        val fragmentManager = this.supportFragmentManager
//                        val fragmentTransaction = fragmentManager.beginTransaction()
//                        fragmentTransaction.replace(R.id.change, Profile_Fragment())
//                        fragmentTransaction.addToBackStack(null)
//                        fragmentTransaction.commit()
                    }
                    is Response.Error -> {
                        Toast.makeText(
                            this,
                            it.errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
//                    is Response.Loading -> {
//                        Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
//                            .show()
//                    }

                }
            })
        }

        binding.RemoveYourProfile.setOnClickListener {
            editViewModel.ProfilePhoto.setValue("https://firebasestorage.googleapis.com/v0/b/connect-dac36.appspot.com/o/images%2F00211165-4379-4f89-94ae-6573ef627344?alt=media&token=26e80b38-4c0b-4c67-80d4-7fa1fdae0c76")
            binding.SetProfilePhoto.load("https://firebasestorage.googleapis.com/v0/b/connect-dac36.appspot.com/o/images%2F00211165-4379-4f89-94ae-6573ef627344?alt=media&token=26e80b38-4c0b-4c67-80d4-7fa1fdae0c76") {
                ImageView.ScaleType.CENTER_CROP
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)

            }
            Toast.makeText(this, "Save your Changes", Toast.LENGTH_SHORT)
                .show()
        }

        binding.ChangeYourProfile.setOnClickListener {
            pickImages()
        }
        }
    private fun pickImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

                Imageuri = data?.getData()!!
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
                            binding.SetProfilePhoto.load(Imageuri) {
                                ImageView.ScaleType.CENTER_CROP
                                crossfade(true)
                                placeholder(R.drawable.ic_launcher_background)

                            }
                            Toast.makeText(this, "Save your Changes", Toast.LENGTH_SHORT)
                                .show()
                            editViewModel.ProfilePhoto.setValue(it.toString())
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
