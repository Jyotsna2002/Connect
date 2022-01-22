package com.example.connect.Views.Dashboard

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.LoginFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.connect.Dashboard.Companion.token
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.Datastore
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.View_model.PostViewModel
import com.example.connect.View_model.PostViewModelFactory
import com.example.connect.databinding.PostFragmentBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.util.*
import android.content.Context
import androidx.appcompat.app.AlertDialog


import com.example.connect.MainActivity
import com.example.connect.R
import android.content.DialogInterface
import android.view.KeyEvent
import android.app.ProgressDialog
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import kotlin.collections.ArrayList


class Post_Fragment : Fragment() {
    companion object{
        lateinit var ok:String
        var choose=1
    }
    private var _binding: PostFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var ImageUri: ArrayList<String>
    private lateinit var Imageuri:Uri
    private var IMAGE_REQUEST_CODE = 100
    var imageSet = 0

    private lateinit var postViewModel: PostViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        ok="3"
        ImageUri= ArrayList()
        val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        val inflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.alert_dialog_profile_picture, null)
        builder?.setView(dialogView)
        val image=dialogView.findViewById<ImageView>(R.id.imageViewADPPCamera)
        val video =dialogView.findViewById<ImageView>(R.id.imageViewADPPGallery)
        val alertDialogProfilePicture: AlertDialog? = builder?.create()
        alertDialogProfilePicture?.setCanceledOnTouchOutside(false)
        alertDialogProfilePicture?.show()
        image.setOnClickListener {
            selectImage()
            ok="1"
            alertDialogProfilePicture?.dismiss()
        }
        video.setOnClickListener {
            pickVideoGallery()
            ok="0"
            alertDialogProfilePicture?.dismiss()
        }
        binding.upload.setOnClickListener {
            if (ok !="1" || ok!="0"){
                binding.upload.isClickable=false
            }
            val caption=binding.captionEditText.text.toString().trim()
            Log.i("caption", "onActivityResult: $caption")
            postViewModel.caption.setValue(caption)
                postViewModel.videoUrl.setValue(listOf(it.toString()))
                Log.i(
                    "Hello",
                    "onActivityResult: " + postViewModel.videoUrl.value
                )

            Log.i(
                "HelloUri2",
                "onActivityResult: $ImageUri"
            )
            if(choose==1)
            postViewModel.submitData(ImageUri)
            else if(choose==0) {

                postViewModel.submitData(ImageUri)
            }
            postViewModel.Result.observe(viewLifecycleOwner, {
                when (it) {
                    is Response.Success ->{ Toast.makeText(context, "Success", Toast.LENGTH_LONG)
                        .show()
                        postViewModel.caption.setValue("")
                        postViewModel.imageUrl.setValue(null)
                      }
                    is Response.Error -> {Toast.makeText(
                        context,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                        }
                    is Response.Loading -> {Toast.makeText(context, "Loading", Toast.LENGTH_LONG)
                        .show()
                 }

                }
            })
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postRepo = UploadPostRepo(ServiceBuilder1.buildService(token))
        Log.i("token", "access:$token")
        val postViewModelFactory = PostViewModelFactory(postRepo)
        postViewModel = ViewModelProvider(this, postViewModelFactory)[PostViewModel::class.java]


    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action = Intent.ACTION_GET_CONTENT
        imageSet = 1
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun pickVideoGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data!!.clipData != null) {
                choose=1
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri

                    val progressDialog = ProgressDialog(context)
                    progressDialog.setMessage("Uploading File...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    val randomKey = UUID.randomUUID().toString()
                    val storageReference =
                        FirebaseStorage.getInstance().getReference("images/" + randomKey)
                    storageReference.putFile(imageUri)
                        .addOnSuccessListener {
                            it.storage.downloadUrl.addOnSuccessListener {
                                binding.post.setImageURI(imageUri)
                                Toast.makeText(context, "successfully Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                                ImageUri!!.add(it.toString())
                                Log.i(
                                    "HelloUri",
                                    "onActivityResult: $ImageUri"
                                )
                                if (progressDialog.isShowing)
                                    progressDialog.dismiss()


                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                            if (progressDialog.isShowing)
                                progressDialog.dismiss()
                        }
                }
            }
            else
            {
                choose=0
              Imageuri = data.getData()!!
                val progressDialog = ProgressDialog(context)
                progressDialog.setMessage("Uploading File...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                val randomKey = UUID.randomUUID().toString()
                val storageReference =
                    FirebaseStorage.getInstance().getReference("images/" + randomKey)
                storageReference.putFile(Imageuri)
                    .addOnSuccessListener {
                        it.storage.downloadUrl.addOnSuccessListener {
                            binding.post.setImageURI(Imageuri)
                            Toast.makeText(context, "successfully Uploaded", Toast.LENGTH_SHORT)
                                .show()
                            postViewModel.imageUrl.setValue(listOf(it.toString()))

                            if (progressDialog.isShowing)
                                progressDialog.dismiss()


                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                        if (progressDialog.isShowing)
                            progressDialog.dismiss()
                    }
            }
        }
    }


}