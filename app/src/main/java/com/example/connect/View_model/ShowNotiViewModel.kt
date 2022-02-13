package com.example.connect.View_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.model.*
import kotlinx.coroutines.launch


class ShowNotiViewModel : ViewModel() {


    private var ShowBookmarkResult:MutableLiveData<Response<List<Notificationpage>>> = MutableLiveData()
    val showNotiResult:LiveData<Response<List<Notificationpage>>>
        get() = ShowBookmarkResult

    fun ShowNotiSubmitData(context: Context) = viewModelScope.launch {
        ShowBookmarkResult =NotiRepo().ShowNoti(context)

    }
}
