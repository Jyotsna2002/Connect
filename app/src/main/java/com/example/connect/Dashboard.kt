package com.example.connect

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.connect.Repository.Datastore
import com.example.connect.Repository.Datastore.Companion.ACCESS_TOKEN_KEY
import com.example.connect.Repository.Datastore.Companion.EMAIL_KEY
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val datastore = this.let { Datastore(it) }

        lifecycleScope.launch {

            Toast.makeText(this@Dashboard, datastore.getUserDetails(EMAIL_KEY), Toast.LENGTH_SHORT).show()
            Toast.makeText(this@Dashboard, datastore.getUserDetails(ACCESS_TOKEN_KEY), Toast.LENGTH_SHORT).show()

        }
        bottomNav = findViewById(R.id.bottomNav)
        navController = findNavController(R.id.fragmentContainerView2)
        bottomNav.setupWithNavController(navController)

    }
}