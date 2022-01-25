package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.*
import com.example.connect.model.CommentDataClass
import com.example.connect.model.LikePostDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class ShowCommentViewModel(private val showCommentRepo: ShowCommentRepo) : ViewModel() {

    var PostId = MutableLiveData<Int>()

    private var ShowCommentResult:MutableLiveData<Response<List<CommentDataClass>>> = MutableLiveData()
    val showCommentResult:LiveData<Response<List<CommentDataClass>>>
        get() = ShowCommentResult

    fun ShowCommentSubmitData() = viewModelScope.launch {
        ShowCommentResult =showCommentRepo.ShowComment(PostId.value)
        Log.i("user_id", "Respone "+PostId.value)

    }
}
