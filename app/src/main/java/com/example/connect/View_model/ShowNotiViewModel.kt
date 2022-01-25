package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.OthersProfile
import com.example.connect.Repository.*
import com.example.connect.model.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class ShowNotiViewModel(private val showBookmarkRepo: NotiRepo) : ViewModel() {


    private var ShowBookmarkResult:MutableLiveData<Response<List<Notificationpage>>> = MutableLiveData()
    val showNotiResult:LiveData<Response<List<Notificationpage>>>
        get() = ShowBookmarkResult

    fun ShowNotiSubmitData() = viewModelScope.launch {
        ShowBookmarkResult =showBookmarkRepo.ShowNoti()

    }
}
