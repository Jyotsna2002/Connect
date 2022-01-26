package com.example.connect

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.*
import com.example.connect.View_model.*
import com.example.connect.Views.Dashboard.Profile_Fragment.Companion.Text
import com.example.connect.databinding.ProfileFragmentBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.OthersProfileAdapter

class OthersProfile : AppCompatActivity() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var othersprofileViewModel: OthersProfileViewModel
    private lateinit var othersprofilepostViewModel: OthersProfilePostViewModel
    private lateinit var sendRequestViewModel: SendRequestViewModel
    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var recyclerView: RecyclerView
    private var adapter = OthersProfileAdapter()
    var userid: Int? = null

    //companion object{
//    lateinit var Text7:TextView
//}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        Text7=binding.textView10
        Text = binding.textView10
        recyclerView = binding.recyclerView2
        gridLayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        val othersshowRepo = OthersProfileRepo(ServiceBuilder1.buildService(Dashboard.token))
        Log.i("token", "access:${Dashboard.token}")
        val othersViewModelFactory = OthersProfileViewModelFactory(othersshowRepo)
        othersprofileViewModel =
            ViewModelProvider(this, othersViewModelFactory)[OthersProfileViewModel::class.java]

        val othersProfilePostRepo =
            OthersProfilePostRepo(ServiceBuilder1.buildService(Dashboard.token))
        Log.i("token", "access:${Dashboard.token}")
        val othersPostViewModelFactory = OthersProfilePostViewModelFactory(othersProfilePostRepo)
        othersprofilepostViewModel = ViewModelProvider(
            this,
            othersPostViewModelFactory
        )[OthersProfilePostViewModel::class.java]

        val sendRequestRepo = SendRequestRepo(ServiceBuilder1.buildService(Dashboard.token))
        Log.i("token", "access:${Dashboard.token}")
        val sendRequestViewModelFactory = SendRequestViewModelFactory(sendRequestRepo)
        sendRequestViewModel =
            ViewModelProvider(this, sendRequestViewModelFactory)[SendRequestViewModel::class.java]

        userid = intent.getStringExtra("USER")?.toInt()
        val UserId = userid.toString()
        othersprofileViewModel.User_id.setValue(userid)
        othersprofileViewModel.submitotherprofile()
        othersprofileViewModel.showotherProfilResult.observe(this, {
            when (it) {
                is Response.Success -> {
//                    Toast.makeText(this, "Success", Toast.LENGTH_LONG)
//                        .show()

                    binding.username.text = it.data?.username
                    binding.userName.text = it.data?.user_name
                    binding.followeres.text = it.data?.no_of_followers.toString()
                    binding.followings.text = it.data?.no_of_following.toString()
                    binding.bio.text = it.data?.bio
                    binding.ProfilePhoto.load(it.data?.profile_photo) {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_background)
                    }
                    binding.editProfile.visibility = View.GONE
                    binding.message.visibility = View.VISIBLE
                    binding.follow.visibility = View.VISIBLE
                    if (it.data?.is_private == true && it.data.is_follow == false) {
                        binding.recyclerView2.visibility = View.GONE
                        binding.AccountPrivate.visibility = View.VISIBLE
                    }
                    if (it.data?.follow == true) {
                        binding.follow.text = "Following"
                        binding.follow.background.setTint(
                            ContextCompat.getColor(
                                this,
                                R.color.gray
                            )
                        )
                        binding.followeres.text = it.data.no_of_followers.toString()
                        binding.followings.text = it.data.no_of_following.toString()
                    } else {
                        binding.follow.text = "Follow"
                        binding.follow.background.setTint(
                            ContextCompat.getColor(
                                this,
                                R.color.pink
                            )
                        )
                        binding.followeres.text = it.data?.no_of_followers.toString()
                        binding.followings.text = it.data?.no_of_following.toString()
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
        othersprofilepostViewModel.User_id.setValue(userid)
        othersprofilepostViewModel.submitotherprofilepost()
        othersprofilepostViewModel.showotherProfilPostResult.observe(this, {
            when (it) {
                is Response.Success -> {
//                    Toast.makeText(this, "Success", Toast.LENGTH_LONG)
//                        .show()

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
        binding.follow.setOnClickListener {
            sendRequestViewModel.User_id.setValue(userid)
            sendRequestViewModel.sendRequest()
            sendRequestViewModel.sendRequestResult.observe(this, {
                when (it) {
                    is Response.Success -> {
//                        Toast.makeText(this, "Request Sent", Toast.LENGTH_LONG)
//                            .show()
                        if (it.data?.follow == true) {
                            binding.follow.text = "Following"
                            binding.follow.background.setTint(
                                ContextCompat.getColor(
                                    this,
                                    R.color.gray
                                )
                            )
                            binding.followeres.text = it.data.no_of_followers.toString()
                            binding.followings.text = it.data.no_of_following.toString()

                        } else {
                            binding.follow.text = "Follow"
                            binding.follow.background.setTint(
                                ContextCompat.getColor(
                                    this,
                                    R.color.pink
                                )
                            )
                            binding.followeres.text = it.data?.no_of_followers.toString()
                            binding.followings.text = it.data?.no_of_following.toString()
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
        }
    }


}