package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.ShowStoryRepo
import com.example.connect.model.ShowStoryDataClass
import kotlinx.coroutines.launch


class ShowStoryViewModel : ViewModel() {

    var UserId = MutableLiveData<Int>()

    private var ShowStoryResult:MutableLiveData<Response<List<ShowStoryDataClass>>> = MutableLiveData()
    val showStoryResult:LiveData<Response<List<ShowStoryDataClass>>>
        get() = ShowStoryResult

    fun ShowStorySubmitData(context: Context) = viewModelScope.launch {
        ShowStoryResult = ShowStoryRepo().ShowStory(UserId.value,context)
        Log.i("userId", "onActivityResult:" +UserId.value)

    }
}
