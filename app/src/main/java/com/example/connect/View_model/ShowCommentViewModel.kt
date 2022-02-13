package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.model.CommentDataClass
import kotlinx.coroutines.launch


class ShowCommentViewModel : ViewModel() {

    var PostId = MutableLiveData<Int>()

    private var ShowCommentResult:MutableLiveData<Response<List<CommentDataClass>>> = MutableLiveData()
    val showCommentResult:LiveData<Response<List<CommentDataClass>>>
        get() = ShowCommentResult

    fun ShowCommentSubmitData(context: Context) = viewModelScope.launch {
        ShowCommentResult =ShowCommentRepo().ShowComment(PostId.value,context)
        Log.i("user_id", "Respone "+PostId.value)

    }
}
