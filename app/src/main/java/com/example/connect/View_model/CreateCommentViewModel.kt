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


class CreateCommentViewModel : ViewModel() {

    var PostId = MutableLiveData<Int>()
    var ParentId = MutableLiveData<Int>()
    var Content = MutableLiveData<String>()

    private var CreateCommentResult:MutableLiveData<Response<CommentDataClass>> = MutableLiveData()
    val createCommentResult:LiveData<Response<CommentDataClass>>
        get() = CreateCommentResult

    fun CreateCommentSubmitData(context: Context) = viewModelScope.launch {
        CreateCommentResult =CreateCommentRepo().CreateComment(PostId.value,ParentId.value,Content.value,context)
        Log.i("user_id", "Respone "+PostId.value+ParentId.value+Content.value)

    }
}
