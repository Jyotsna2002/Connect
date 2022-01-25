package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.LikePostRepo

class LikeStoryViewModelFactory(val homeRepo: LikePostRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LikePostRepo::class.java).newInstance(homeRepo)
    }
}