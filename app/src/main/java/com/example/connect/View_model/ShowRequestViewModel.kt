package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.ShowRequestPageRepo
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Views.Dashboard.Post_Fragment
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.ShowFollowRequestDataClass
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.ResponseBody

class ShowRequestViewModel(private val showRequestRepo: ShowRequestPageRepo) : ViewModel()  {
    private var ShowRequestResult: MutableLiveData<Response<List<ShowFollowRequestDataClass>>> = MutableLiveData()
    val showRequestResult: LiveData<Response<List<ShowFollowRequestDataClass>>>
        get() = ShowRequestResult
    fun showRequest() = viewModelScope.launch {
        ShowRequestResult= showRequestRepo.ShowRequest()
        Log.i("post", "onActivityResult:$ShowRequestResult")
    }
}