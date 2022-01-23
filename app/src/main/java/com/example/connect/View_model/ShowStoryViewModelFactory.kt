package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.*

class ShowStoryViewModelFactory(val homeRepo: ShowStoryRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ShowStoryRepo::class.java).newInstance(homeRepo)
    }
}