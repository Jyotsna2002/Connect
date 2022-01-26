package com.example.connect.Views.Dashboard.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.connect.OthersProfile
import com.example.connect.Post
import com.example.connect.Repository.Response
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.Repository.SearchTagRepo
import com.example.connect.View_model.SearchProfileViewModel
import com.example.connect.View_model.SearchProfileViewModelFactory
import com.example.connect.View_model.SearchTagViewModel
import com.example.connect.View_model.SearchTagViewModelFactory
import com.example.connect.databinding.AccountsBinding
import com.example.connect.databinding.SearchFragmentBinding
import com.example.connect.databinding.TagsBinding
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import com.example.connect.recylcer_view_adapter.SearchProfileAdapter
import com.example.connect.recylcer_view_adapter.SearchTagAdapter

class TagsSearch_Fragment: Fragment() {
    private var _binding: TagsBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchTagViewModel:SearchTagViewModel
    private lateinit var recyclerView: RecyclerView
    var adapter= SearchTagAdapter()
    companion object{
        lateinit var Text3:TextView
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TagsBinding.inflate(inflater, container, false)
        val view = binding.root
        Text3=binding.textView12
        recyclerView= binding.tags
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val searchTag=binding.searchTagEditText
        searchTag.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    searchTagViewModel.submitotherprofile(s.toString())
                    Log.i("View", "ontext")
                    searchTagViewModel.searchTagResult.observe(viewLifecycleOwner, {
                        when (it) {
                            is Response.Success -> {

                                adapter.setUpdatedData(it.data as ArrayList<SearchTagDataClass>)
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

            override fun afterTextChanged(s: Editable?) {
            }
        })
        adapter.setOnItemClickListener(object : SearchTagAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, Post::class.java)
                intent.putExtra("USER", adapter.Posts[position].tag.toString())
                Log.i("userId", "onActivityResult:" +adapter.Posts[position].tag.toString())
                startActivity(intent)
            }
        })
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchTagRepo= SearchTagRepo(ServiceBuilder1.buildService(Dashboard.token))
        Log.i("tokenmyaccount", "access:${Dashboard.token}")
        val searchTagViewModelFactory= SearchTagViewModelFactory(searchTagRepo)
        searchTagViewModel= ViewModelProvider(this,searchTagViewModelFactory)[SearchTagViewModel::class.java]
    }
}