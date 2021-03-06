package com.example.connect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Datastore.Companion.ACCESS_TOKEN_KEY
import com.example.connect.Password_check.Datastore.Companion.NAME_KEY
import com.example.connect.Password_check.Datastore.Companion.REF_TOKEN_KEY
import com.example.connect.Password_check.Datastore.Companion.USER_KEY
import com.example.connect.Password_check.Datastore.Companion.USER_NAME_KEY
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    companion object{
        var token: String? =null
        var refresh: String? =null
        lateinit var name: String
        lateinit var username:String
        lateinit var user:String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val datastore = this.let { Datastore(it) }

      lifecycleScope.launch {
          token=  datastore.getUserDetails(ACCESS_TOKEN_KEY).toString()
          name= datastore.getUserDetails(NAME_KEY).toString()
          username=datastore.getUserDetails(USER_NAME_KEY).toString()
          user=datastore.getUserDetails(USER_KEY).toString()
          refresh=  datastore.getUserDetails(REF_TOKEN_KEY).toString()
//            Toast.makeText(this@Dashboard, datastore.getUserDetails(EMAIL_KEY), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this@Dashboard, datastore.getUserDetails(ACCESS_TOKEN_KEY), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this@Dashboard, datastore.getUserDetails(USER_NAME_KEY), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this@Dashboard, datastore.getUserDetails(USER_KEY), Toast.LENGTH_SHORT).show()


        }

        bottomNav = findViewById(R.id.bottomNav)
        navController = findNavController(R.id.fragmentContainerView2)
        bottomNav.setupWithNavController(navController)

    }
}