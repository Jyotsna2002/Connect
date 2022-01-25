package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.CreateCommentRepo
import com.example.connect.Repository.LikePostRepo

class CreateCommentViewModelFactory(val homeRepo: CreateCommentRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateCommentRepo::class.java).newInstance(homeRepo)
    }
}