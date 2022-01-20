package com.example.connect.Views.Dashboard.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.connect.Views.Dashboard.search.SearchTabAdapter
import com.example.connect.databinding.NotificationFragmentBinding
import com.example.connect.databinding.RequestItemsBinding
import com.google.android.material.tabs.TabLayoutMediator

class Notification_Fragment : Fragment() {
    private var _binding: NotificationFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val notificationViewPager = binding.viewpagerNotification
        val notificationTabLayout = binding.tabNotification
        val notiPageAdapter = NotificationTabAdapter(this)
        notificationViewPager.adapter = notiPageAdapter
        TabLayoutMediator(notificationTabLayout, notificationViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Liked post"
                1 -> tab.text = "Friend request"
            }
        }.attach()
        return view
    }

}