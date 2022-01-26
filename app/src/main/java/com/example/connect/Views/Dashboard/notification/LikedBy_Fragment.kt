package com.example.connect.Views.Dashboard.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Dashboard
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.NotiRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.ShowRequestPageRepo
import com.example.connect.View_model.NotiViewModelFactory
import com.example.connect.View_model.ShowNotiViewModel
import com.example.connect.View_model.ShowRequestViewModel
import com.example.connect.View_model.ShowRequestViewModelFactory
import com.example.connect.databinding.AccountsBinding
import com.example.connect.databinding.LikedByBinding
import com.example.connect.databinding.SearchFragmentBinding
import com.example.connect.model.Notificationpage
import com.example.connect.model.ShowFollowRequestDataClass
import com.example.connect.recylcer_view_adapter.NotiAdapter
import com.example.connect.recylcer_view_adapter.ShowRequestPageAdapter

class LikedBy_Fragment: Fragment() {
    private var _binding: LikedByBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var adapter=NotiAdapter()
    private lateinit var showNotiViewModel: ShowNotiViewModel
    companion object{
        lateinit var Text7:TextView
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LikedByBinding.inflate(inflater, container, false)
        val view = binding.root
        Text7=binding.likeP
        recyclerView= binding.LikedByRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val showNotiRepo = NotiRepo(ServiceBuilder1.buildService(Dashboard.token))
        val showNotiViewModelFactory = NotiViewModelFactory(showNotiRepo)
        showNotiViewModel= ViewModelProvider(this, showNotiViewModelFactory)[ShowNotiViewModel::class.java]
        showNotiViewModel.ShowNotiSubmitData()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNotiViewModel.showNotiResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success -> {

                    adapter.setUpdatedData(it.data as ArrayList<Notificationpage>)

                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Response.Loading -> {

                }

            }
        })
    }
}