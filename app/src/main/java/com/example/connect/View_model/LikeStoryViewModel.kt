package com.example.connect.View_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.model.LikePostDataClass
import kotlinx.coroutines.launch


class LikeStoryViewModel : ViewModel() {

    var PostId = MutableLiveData<Int>()

    private var LikePostStoryResult:MutableLiveData<Response<LikePostDataClass>> = MutableLiveData()
    val likePostStoryResult:LiveData<Response<LikePostDataClass>>
        get() = LikePostStoryResult

    fun LikeStorySubmitData(context: Context) = viewModelScope.launch {
        LikePostStoryResult =LikePostRepo().LikePost(PostId.value,context)

    }
}
