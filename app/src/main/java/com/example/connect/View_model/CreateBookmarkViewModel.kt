package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.*
import com.example.connect.model.CommentDataClass
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.LikePostDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class CreateBookmarkViewModel(private val createBookmarkRepo: CreateBookmarkRepo) : ViewModel() {

    var PostId = MutableLiveData<Int>()

    private var CreateBookmarkResult:MutableLiveData<Response<HomeDataClassItem>> = MutableLiveData()
    val createBookmarkResult:LiveData<Response<HomeDataClassItem>>
        get() = CreateBookmarkResult

    fun CreateBookmarkSubmitData() = viewModelScope.launch {
        CreateBookmarkResult =createBookmarkRepo.CreateBookmark(PostId.value)
        Log.i("user_id", "Respone "+PostId.value)

    }
}
