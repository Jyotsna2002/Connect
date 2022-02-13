package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.model.HomeDataClassItem
import kotlinx.coroutines.launch


class CreateBookmarkViewModel: ViewModel() {

    var PostId = MutableLiveData<Int>()

    private var CreateBookmarkResult:MutableLiveData<Response<HomeDataClassItem>> = MutableLiveData()
    val createBookmarkResult:LiveData<Response<HomeDataClassItem>>
        get() = CreateBookmarkResult

    fun CreateBookmarkSubmitData(context: Context) = viewModelScope.launch {
        CreateBookmarkResult =CreateBookmarkRepo().CreateBookmark(PostId.value,context)
        Log.i("user_id", "Respone "+PostId.value)

    }
}
