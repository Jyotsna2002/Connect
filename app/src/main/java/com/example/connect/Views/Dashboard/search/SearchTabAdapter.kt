package com.example.connect.Views.Dashboard.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchTabAdapter(fag: Fragment) : FragmentStateAdapter(fag) {
    override fun getItemCount(): Int = 2
companion object{
    lateinit var type:String
}
    override fun createFragment(position: Int): Fragment{
        return when(position) {
            0 -> {
                type="0"
                AcoountSearch_Fragment()
            }
            1 -> {
                type="1"
                TagsSearch_Fragment()
            }
            else -> {
                AcoountSearch_Fragment()
            }
        }
    }
}