package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.Repository.UploadPostRepo

class SearchProfileViewModelFactory(val searchProfileRepo: SearchProfileRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SearchProfileRepo::class.java).newInstance(searchProfileRepo)
    }
}