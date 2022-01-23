package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.AcceptRequestRepo
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.HomeStoryRepo
import com.example.connect.Repository.UploadPostRepo

class HomeStoryViewModelFactory(val homeRepo: HomeStoryRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeStoryRepo::class.java).newInstance(homeRepo)
    }
}