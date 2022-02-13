package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Password_check.Response
import com.example.connect.model.HomeDataClassItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel()  {
    private var ShowpostResult: MutableLiveData<Response<List<HomeDataClassItem>>> = MutableLiveData()
    val showpostResult: LiveData<Response<List<HomeDataClassItem>>>
        get() = ShowpostResult
    fun submitPost(context: Context) = viewModelScope.launch {
        ShowpostResult= HomePageRepo().ShowPost(context)

    }
}