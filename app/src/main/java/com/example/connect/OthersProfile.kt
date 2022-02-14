package com.example.connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.datastore
import com.example.connect.View_model.OthersProfilePostViewModel
import com.example.connect.View_model.OthersProfileViewModel
import com.example.connect.View_model.SendRequestViewModel
import com.example.connect.Views.Dashboard.Profile_Fragment.Companion.Text
import com.example.connect.databinding.ProfileFragmentBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.OthersProfileAdapter
import kotlinx.coroutines.launch

class OthersProfile : AppCompatActivity() {
    private lateinit var binding: ProfileFragmentBinding
    private val othersprofileViewModel: OthersProfileViewModel by viewModels()
    private val othersprofilepostViewModel: OthersProfilePostViewModel by viewModels()
    private val sendRequestViewModel: SendRequestViewModel by viewModels()
    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var recyclerView: RecyclerView
    private var adapter = OthersProfileAdapter()
    lateinit var datastore: Datastore
    var userid: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Text = binding.textView10
        val drawerLayout=binding.drawerLayout
        val navView = binding.navView
        recyclerView = binding.recyclerView2
        gridLayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OthersProfileAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@OthersProfile, ShowPost::class.java)
                intent.putExtra("USER", adapter.Posts[position].user.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].user.toString())
                startActivity(intent)
            }
        })
        binding.icondrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        userid = intent.getStringExtra("USER")?.toInt()
        Log.i("useriddddddddd", "onCreate: "+userid)
        val UserId = userid.toString()
        othersprofileViewModel.User_id.setValue(userid)
        othersprofileViewModel.submitotherprofile(this)
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
                    if(it.data?.profile_photo==null){
                        binding.ProfilePhoto.setImageResource(R.drawable.photo)
                    }else {
                        binding.ProfilePhoto.load(it.data.profile_photo) {
                            crossfade(true)
                            placeholder(R.drawable.ic_launcher_background)
                        }
                    }
                    binding.editProfile.visibility = View.GONE
                    binding.message.visibility = View.VISIBLE
                    binding.follow.visibility = View.VISIBLE
                    if (it.data?.is_private == true && it.data.is_follow == false) {
                        binding.recyclerView2.visibility = View.GONE
                        binding.AccountPrivate.visibility = View.VISIBLE
                       // binding.textView10.visibility=View.GONE
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
        othersprofilepostViewModel.submitotherprofilepost(this)
        othersprofilepostViewModel.showotherProfilPostResult.observe(this, {
            when (it) {
                is Response.Success -> {
//                    Toast.makeText(this, "Success", Toast.LENGTH_LONG)
//                        .show()

                    adapter.setUpdatedData(it.data as ArrayList<OthersPost>)
                }
//                is Response.Error -> {
//                    Toast.makeText(
//                        this,
//                        it.errorMessage,
//                        Toast.LENGTH_LONG
//                    ).show()
//                }


            }
        })
        binding.follow.setOnClickListener {
            sendRequestViewModel.User_id.setValue(userid)
            sendRequestViewModel.sendRequest(this)
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

        val intent = Intent(this, MainActivity::class.java)
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Sign Out")
            .setMessage("Are you sure you want to Sign Out ?")
            .setPositiveButton("Sign out") { dialog, id ->
                lifecycleScope.launch {
                    datastore = Datastore(this@OthersProfile)
                    datastore.changeLoginState(false)
                    finish()
                    startActivity(intent)
                }
            }
            .setNeutralButton("Cancel") { dialog, id ->
            }
        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.bookmark -> {
                    val intent = Intent(this, Bookmark::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                }
                R.id.signout -> {
                    drawerLayout.closeDrawers()
                    val alertDialog: android.app.AlertDialog = builder.create()
                    alertDialog.show()
                }
            }
            true
        }
    }


}