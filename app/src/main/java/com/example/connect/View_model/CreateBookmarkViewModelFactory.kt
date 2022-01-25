package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.CreateBookmarkRepo
import com.example.connect.Repository.CreateCommentRepo
import com.example.connect.Repository.LikePostRepo

class CreateBookmarkViewModelFactory(val homeRepo: CreateBookmarkRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateBookmarkRepo::class.java).newInstance(homeRepo)
    }
}