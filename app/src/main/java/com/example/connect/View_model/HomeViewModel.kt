package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Views.Dashboard.Post_Fragment
import com.example.connect.model.HomeDataClassItem
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.ResponseBody

class HomeViewModel(private val showpostRepo: HomePageRepo) : ViewModel()  {
    private var ShowpostResult: MutableLiveData<Response<List<HomeDataClassItem>>> = MutableLiveData()
    val showpostResult: LiveData<Response<List<HomeDataClassItem>>>
        get() = ShowpostResult
    fun submitPost() = viewModelScope.launch {
        ShowpostResult= showpostRepo.ShowPost()
        Log.i("post", "onActivityResult:"+showpostResult)
    }
}