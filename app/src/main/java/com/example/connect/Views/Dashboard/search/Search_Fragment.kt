package com.example.connect.Views.Dashboard.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.connect.R
import com.example.connect.databinding.SearchFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Search_Fragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewPager = binding.viewpagerSearch
        val tabLayout = binding.tabSearch
        tablayout=view.findViewById(R.id.tab_Search)
        viewpager=view.findViewById(R.id.viewpager_Search)
        val searchpageAdapter = SearchTabAdapter(this)
        viewPager.adapter = searchpageAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Accounts"
                1 -> tab.text = "Tags"
            }
        }.attach()



        return view
    }

}
