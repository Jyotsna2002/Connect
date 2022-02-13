package com.example.connect.View_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.model.OthersPost
import kotlinx.coroutines.launch


class ShowBookmarkViewModel : ViewModel() {


    private var ShowBookmarkResult:MutableLiveData<Response<List<OthersPost>>> = MutableLiveData()
    val showBookmarkResult:LiveData<Response<List<OthersPost>>>
        get() = ShowBookmarkResult

    fun ShowBookmarkSubmitData(context:Context) = viewModelScope.launch {
        ShowBookmarkResult =ShowBookmarkRepo().ShowBookmark(context)

    }
}
