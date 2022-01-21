package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.SendRequestRepo
import com.example.connect.Repository.UploadPostRepo

class SendRequestViewModelFactory(val sendRepo: SendRequestRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SendRequestRepo::class.java).newInstance(sendRepo)
    }
}