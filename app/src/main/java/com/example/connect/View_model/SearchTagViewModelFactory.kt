package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.Repository.SearchTagRepo
import com.example.connect.Repository.UploadPostRepo

class SearchTagViewModelFactory(val searchTagRepo: SearchTagRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SearchTagRepo::class.java).newInstance(searchTagRepo)
    }
}