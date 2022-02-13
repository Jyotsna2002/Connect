package com.example.connect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.connect.Password_check.Datastore
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    lateinit var datastore: Datastore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            datastore = Datastore(this@SplashScreenActivity)
            if (datastore.isLogin()) {
                startActivity(Intent(this@SplashScreenActivity, Dashboard::class.java))
                finish()
            }
            else{
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
        }
        }
    }
}