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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Dashboard
import com.example.connect.Dashboard.Companion.user
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.OthersProfile
import com.example.connect.R
import com.example.connect.Repository.Response
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.View_model.SearchProfileViewModel
import com.example.connect.View_model.SearchProfileViewModelFactory
import com.example.connect.Views.Dashboard.Profile_Fragment

import com.example.connect.databinding.AccountsBinding
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import com.example.connect.recylcer_view_adapter.SearchProfileAdapter

class AcoountSearch_Fragment: Fragment() {
    private var _binding: AccountsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchProfileViewModel: SearchProfileViewModel
        var adapter= SearchProfileAdapter()
companion object{
    lateinit var Text2:TextView
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AccountsBinding.inflate(inflater, container, false)
        val view = binding.root
        Text2=binding.textView11
        recyclerView= binding.accounts
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val search=binding.searchEditText
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                   searchProfileViewModel.submitotherprofile(s.toString())
                    Log.i("View", "ontext")
                    searchProfileViewModel.searchProfilResult.observe(viewLifecycleOwner, {
                        when (it) {
                            is Response.Success -> {

                                adapter.setUpdatedData(it.data as ArrayList<SearchProfileDataClassItem>)
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

            override fun afterTextChanged(s: Editable?) {
            }
        })
        adapter.setOnItemClickListener(object : SearchProfileAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                if( adapter.Posts[position].user.toString()==user){
                    Navigation.findNavController(view)
                        .navigate(R.id.action_search_Fragment_to_profile_Fragment)
                }
                else {
                    val intent = Intent(context, OthersProfile::class.java)
                    intent.putExtra("USER", adapter.Posts[position].user.toString())
                    Log.i("userId", "onActivityResult:" + adapter.Posts[position].user.toString())
                    startActivity(intent)
                }
            }
        })
        return view
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchProfileRepo= SearchProfileRepo(ServiceBuilder1.buildService(Dashboard.token))
        Log.i("tokenmyaccount", "access:${Dashboard.token}")
        val searchProfileViewModelFactory= SearchProfileViewModelFactory(searchProfileRepo)
        searchProfileViewModel= ViewModelProvider(this,searchProfileViewModelFactory)[SearchProfileViewModel::class.java]
    }


}