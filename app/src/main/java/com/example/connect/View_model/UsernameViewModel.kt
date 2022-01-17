package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Repository.UsernameRepo
import com.example.connect.Views.Dashboard.Post_Fragment
import com.example.connect.model.AuthDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

//class UsernameViewModel(private val usernameRepo: UsernameRepo) : ViewModel()  {
//    var username =  MutableLiveData<String>()
//    private var UsernameResult:MutableLiveData<Response<AuthDataClass>> = MutableLiveData()
//    val usernameResult: LiveData<Response<AuthDataClass>>
//        get() = UsernameResult
//    fun submitUesrname() = viewModelScope.launch {
//
//        UsernameResult= usernameRepo.CreateUsername(username.value)
//    }
//}