package com.example.connect.Views.Dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Dashboard
import com.example.connect.Dashboard.Companion.user
import com.example.connect.EditProfile
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.OthersProfile
import com.example.connect.R
import com.example.connect.Repository.OthersProfilePostRepo
import com.example.connect.Repository.Response
import com.example.connect.View_model.OthersProfilePostViewModel
import com.example.connect.View_model.OthersProfilePostViewModelFactory
import com.example.connect.databinding.ProfileFragmentBinding
import com.example.connect.model.OthersPost
import com.example.connect.recylcer_view_adapter.OthersProfileAdapter

class Profile_Fragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var othersprofilepostViewModel: OthersProfilePostViewModel
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var recyclerView: RecyclerView
    private var IMAGE_REQUEST_CODE = 100
    private var adapter= OthersProfileAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView= binding.recyclerView2
        gridLayoutManager= GridLayoutManager(context,3,
            LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=gridLayoutManager
        recyclerView.adapter=adapter
        othersprofilepostViewModel.User_id.setValue(user.toInt())
        othersprofilepostViewModel.submitotherprofilepost()
        binding.editProfile.setOnClickListener {
            val intent = Intent(context, EditProfile::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val othersProfilePostRepo= OthersProfilePostRepo(ServiceBuilder1.buildService(Dashboard.token))
        Log.i("tokenmyprofile", "access:${Dashboard.token}")
        val othersPostViewModelFactory= OthersProfilePostViewModelFactory(othersProfilePostRepo)
        othersprofilepostViewModel= ViewModelProvider(this,othersPostViewModelFactory)[OthersProfilePostViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        othersprofilepostViewModel.showotherProfilPostResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success ->{ Toast.makeText(context, "Success", Toast.LENGTH_LONG)
                    .show()

                    adapter.setUpdatedData(it.data as ArrayList<OthersPost>)
                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Response.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG)
                        .show()
                }

            }
        })
    }
}