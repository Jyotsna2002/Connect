package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.UploadPostRepo

class PostViewModelFactory(val postRepo: UploadPostRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UploadPostRepo::class.java).newInstance(postRepo)
    }
}