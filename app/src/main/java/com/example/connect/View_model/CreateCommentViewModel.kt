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


class CreateCommentViewModel(private val createCommentRepo: CreateCommentRepo) : ViewModel() {

    var PostId = MutableLiveData<Int>()
    var ParentId = MutableLiveData<Int>()
    var Content = MutableLiveData<String>()

    private var CreateCommentResult:MutableLiveData<Response<CommentDataClass>> = MutableLiveData()
    val createCommentResult:LiveData<Response<CommentDataClass>>
        get() = CreateCommentResult

    fun CreateCommentSubmitData() = viewModelScope.launch {
        CreateCommentResult =createCommentRepo.CreateComment(PostId.value,ParentId.value,Content.value)
        Log.i("user_id", "Respone "+PostId.value+ParentId.value+Content.value)

    }
}
