package com.example.connect.Views.Dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.*
import com.example.connect.Dashboard.Companion.name
import com.example.connect.Dashboard.Companion.user
import com.example.connect.Dashboard.Companion.username
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.View_model.OthersProfilePostViewModel
import com.example.connect.View_model.OthersProfileViewModel
import com.example.connect.databinding.ProfileFragmentBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import com.example.connect.recylcer_view_adapter.OthersProfileAdapter
import kotlinx.coroutines.launch


class Profile_Fragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var othersprofilepostViewModel: OthersProfilePostViewModel
    private lateinit var othersprofileViewModel: OthersProfileViewModel
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var recyclerView: RecyclerView
    private lateinit var photo:String
    private lateinit var check :String
    private var adapter= OthersProfileAdapter()
    lateinit var datastore: Datastore
    companion object{
        lateinit var Text:TextView
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.username.text=username
        binding.userName.text=name
        val drawerLayout=binding.drawerLayout
        val navView = binding.navView
        Text=binding.textView10
        binding.editProfile.visibility=View.VISIBLE
        recyclerView= binding.recyclerView2
        gridLayoutManager= GridLayoutManager(context,3,
            LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=gridLayoutManager
        recyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : OthersProfileAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ShowPost::class.java)
                intent.putExtra("USER", adapter.Posts[position].user.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].user.toString())
                startActivity(intent)
            }
        })
        othersprofilepostViewModel.User_id.setValue(user.toInt())
        othersprofilepostViewModel.submitotherprofilepost(requireContext())
        binding.editProfile.setOnClickListener {
            val intent = Intent(context, EditProfile::class.java)
            intent.putExtra("Photo", photo)
            intent.putExtra("CHECK",check)
            startActivity(intent)
        }
        binding.icondrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        val intent = Intent(context, MainActivity::class.java)
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Sign Out")
            .setMessage("Are you sure you want to Sign Out ?")
            .setPositiveButton("Sign out") { dialog, id ->
                lifecycleScope.launch {
                    datastore = Datastore(requireContext())
                    datastore.changeLoginState(false)
                    activity?.finish()
                    startActivity(intent)
                }
            }
            .setNeutralButton("Cancel") { dialog, id ->
            }
        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.bookmark -> {
                    val intent = Intent(context, Bookmark::class.java)
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
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        othersprofilepostViewModel= ViewModelProvider((context as FragmentActivity?)!!)[OthersProfilePostViewModel::class.java]

        othersprofileViewModel = ViewModelProvider((context as FragmentActivity?)!!)[OthersProfileViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        othersprofilepostViewModel.showotherProfilPostResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success ->{

                    adapter.setUpdatedData(it.data as ArrayList<OthersPost>)

                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        })
        othersprofileViewModel.User_id.setValue(user.toInt())
        othersprofileViewModel.submitotherprofile(requireContext())
        othersprofileViewModel.showotherProfilResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success ->{

                    binding.username.text=it.data?.username
                    binding.userName.text=it.data?.user_name
                    binding.followeres.text= it.data?.no_of_followers.toString()
                    binding.followings.text=it.data?.no_of_following.toString()
                    binding.bio.text=it.data?.bio
                    photo= it.data?.profile_photo.toString()
                    check= it.data?.is_private.toString()
                    if (it.data?.profile_photo==null){
                        binding.ProfilePhoto.setImageResource(R.drawable.photo)
                    }
                    else {
                        binding.ProfilePhoto.load(it.data.profile_photo) {
                            ImageView.ScaleType.CENTER_CROP
                            crossfade(true)
                            placeholder(R.drawable.ic_launcher_background)
                        }
                    }
                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        })
    }
}