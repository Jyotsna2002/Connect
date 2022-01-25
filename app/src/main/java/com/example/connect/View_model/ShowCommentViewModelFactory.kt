package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.CreateCommentRepo
import com.example.connect.Repository.LikePostRepo
import com.example.connect.Repository.ShowCommentRepo

class ShowCommentViewModelFactory(val homeRepo: ShowCommentRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ShowCommentRepo::class.java).newInstance(homeRepo)
    }
}