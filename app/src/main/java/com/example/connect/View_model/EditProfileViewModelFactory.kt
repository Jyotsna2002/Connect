package com.example.connect.View_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.connect.Repository.AcceptRequestRepo
import com.example.connect.Repository.EditProfileRepo
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.UploadPostRepo

class EditProfileViewModelFactory(val homeRepo: EditProfileRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EditProfileRepo::class.java).newInstance(homeRepo)
    }
}