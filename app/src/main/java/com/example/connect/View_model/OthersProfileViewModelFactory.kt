package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.UploadPostRepo

class OthersProfileViewModelFactory(val otherRepo: OthersProfileRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OthersProfileRepo::class.java).newInstance(otherRepo)
    }
}