package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.CreateBookmarkRepo
import com.example.connect.Repository.CreateCommentRepo
import com.example.connect.Repository.LikePostRepo
import com.example.connect.Repository.ShowBookmarkRepo

class ShowBookmarkViewModelFactory(val homeRepo: ShowBookmarkRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ShowBookmarkRepo::class.java).newInstance(homeRepo)
    }
}