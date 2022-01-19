package com.example.connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.connect.databinding.EditFragmentBinding
import com.example.connect.databinding.ProfileFragmentBinding

class EditProfile : AppCompatActivity() {
    private lateinit var binding: EditFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = EditFragmentBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
    }
}