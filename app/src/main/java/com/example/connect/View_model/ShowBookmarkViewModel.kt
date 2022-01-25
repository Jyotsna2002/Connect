package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.OthersProfile
import com.example.connect.Repository.*
import com.example.connect.model.CommentDataClass
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.LikePostDataClass
import com.example.connect.model.OthersPost
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class ShowBookmarkViewModel(private val showBookmarkRepo: ShowBookmarkRepo) : ViewModel() {


    private var ShowBookmarkResult:MutableLiveData<Response<List<OthersPost>>> = MutableLiveData()
    val showBookmarkResult:LiveData<Response<List<OthersPost>>>
        get() = ShowBookmarkResult

    fun ShowBookmarkSubmitData() = viewModelScope.launch {
        ShowBookmarkResult =showBookmarkRepo.ShowBookmark()

    }
}
