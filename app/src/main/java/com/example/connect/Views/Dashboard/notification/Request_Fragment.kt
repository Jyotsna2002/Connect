package com.example.connect.Views.Dashboard.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Dashboard
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Repository.AcceptRequestRepo
import com.example.connect.Password_check.Response
import com.example.connect.Repository.ShowRequestPageRepo
import com.example.connect.View_model.*
import com.example.connect.databinding.RequestBinding
import com.example.connect.model.ShowFollowRequestDataClass
import com.example.connect.recylcer_view_adapter.ShowRequestPageAdapter

class Request_Fragment: Fragment() {
    private var _binding: RequestBinding? = null
    private lateinit var recyclerView: RecyclerView
    private var adapter= ShowRequestPageAdapter()
    var id: Int?=null
    private val binding get() = _binding!!
    private lateinit var showRequestViewModel: ShowRequestViewModel
    private lateinit var acceptRequestViewModel: AcceptRequestViewModel
    companion object{
        lateinit var Text4:TextView
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RequestBinding.inflate(inflater, container, false)
        val view = binding.root
        Text4=binding.textView13
        recyclerView= binding.requestRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : ShowRequestPageAdapter.onItemClickListener1 {
            override fun onItemClick(position: Int) {
                id=adapter.Posts[position].id
                acceptRequestViewModel.Follow_id.setValue(id)
                acceptRequestViewModel.Confirm.setValue("true")
                acceptRequestViewModel.submitotherprofile(requireContext())
                acceptRequestViewModel.acceptRequestResult.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {
                            Toast.makeText(context, "Request Accepted", Toast.LENGTH_LONG)
                                .show()

                            showRequestViewModel.showRequest(requireContext())
                            showRequestViewModel.showRequestResult.observe(viewLifecycleOwner, {
                                when (it) {
                                    is Response.Success -> {

                                        adapter.setUpdatedData(it.data as ArrayList<ShowFollowRequestDataClass>)

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
//                            adapter.Posts.removeAt(position)
//                            adapter.notifyDataSetChanged()
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
        })
        adapter.setOnItemClickListener2(object : ShowRequestPageAdapter.onItemClickListener2 {
            override fun onItemClick2(position: Int) {
                id=adapter.Posts[position].id
                acceptRequestViewModel.Follow_id.setValue(id)
                acceptRequestViewModel.Confirm.setValue("false")
                acceptRequestViewModel.submitotherprofile(requireContext())
                acceptRequestViewModel.acceptRequestResult.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {
                            Toast.makeText(context, "Request Deleted", Toast.LENGTH_LONG)
                                .show()

                            showRequestViewModel.showRequest(requireContext())
                            showRequestViewModel.showRequestResult.observe(viewLifecycleOwner, {
                                when (it) {
                                    is Response.Success -> {

                                        adapter.setUpdatedData(it.data as ArrayList<ShowFollowRequestDataClass>)

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
//                            adapter.Posts.removeAt(position)
//                            adapter.notifyDataSetChanged()
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
        })
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showRequestViewModel = ViewModelProvider((context as FragmentActivity?)!!)[ShowRequestViewModel::class.java]



        acceptRequestViewModel = ViewModelProvider((context as FragmentActivity?)!!)[AcceptRequestViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRequestViewModel.showRequest(requireContext())
        showRequestViewModel.showRequestResult.observe(viewLifecycleOwner, {
            when (it) {
                is Response.Success -> {

                    adapter.setUpdatedData(it.data as ArrayList<ShowFollowRequestDataClass>)

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