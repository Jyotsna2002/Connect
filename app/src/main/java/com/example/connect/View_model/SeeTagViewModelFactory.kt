package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.SeeTagPostRepo
import com.example.connect.Repository.UploadPostRepo

class SeeTagViewModelFactory(val seeTagRepo: SeeTagPostRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SeeTagPostRepo::class.java).newInstance(seeTagRepo)
    }
}