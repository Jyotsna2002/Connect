package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.OthersProfilePostRepo
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.ShowRequestPageRepo
import com.example.connect.Repository.UploadPostRepo

class ShowRequestViewModelFactory(val otherRepo: ShowRequestPageRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ShowRequestPageRepo::class.java).newInstance(otherRepo)
    }
}