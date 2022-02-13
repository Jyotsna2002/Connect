package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.ShowRequestPageRepo
import com.example.connect.model.ShowFollowRequestDataClass
import kotlinx.coroutines.launch

class ShowRequestViewModel : ViewModel()  {
    private var ShowRequestResult: MutableLiveData<Response<List<ShowFollowRequestDataClass>>> = MutableLiveData()
    val showRequestResult: LiveData<Response<List<ShowFollowRequestDataClass>>>
        get() = ShowRequestResult
    fun showRequest(context: Context) = viewModelScope.launch {
        ShowRequestResult= ShowRequestPageRepo().ShowRequest(context)
        Log.i("post", "onActivityResult:$ShowRequestResult")
    }
}