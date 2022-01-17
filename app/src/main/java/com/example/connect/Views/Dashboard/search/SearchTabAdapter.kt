package com.example.connect.Views.Dashboard.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchTabAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment{
        return when(position) {
            0 -> {
                AcoountSearch_Fragment()
            }
            1 -> {
                TagsSearch_Fragment()
            }
            else -> {
                AcoountSearch_Fragment()
            }
        }
    }
}