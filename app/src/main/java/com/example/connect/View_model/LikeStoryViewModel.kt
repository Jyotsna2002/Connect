package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.*
import com.example.connect.model.LikePostDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class LikeStoryViewModel(private val likeStoryRepo: LikePostRepo) : ViewModel() {

    var PostId = MutableLiveData<Int>()

    private var LikePostStoryResult:MutableLiveData<Response<LikePostDataClass>> = MutableLiveData()
    val likePostStoryResult:LiveData<Response<LikePostDataClass>>
        get() = LikePostStoryResult

    fun LikeStorySubmitData() = viewModelScope.launch {
        LikePostStoryResult =likeStoryRepo.LikePost(PostId.value)

    }
}
