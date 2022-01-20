package com.example.connect.Views.Dashboard.notification

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class NotificationTabAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment{
        return when(position) {
            0 -> {
                LikedBy_Fragment()
            }
            1 -> {
                Request_Fragment()
            }
            else -> {
                LikedBy_Fragment()
            }
        }
    }
}