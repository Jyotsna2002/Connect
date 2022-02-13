package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.HomeStoryRepo
import com.example.connect.Password_check.Response
import com.example.connect.model.HomeStoryDataClass
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class HomeStoryViewModel : ViewModel()  {
    private var HomeStoryResult: MutableLiveData<Response<List<HomeStoryDataClass>>> = MutableLiveData()
    val homeStoryResult: LiveData<Response<List<HomeStoryDataClass>>>
        get() = HomeStoryResult
    fun HomeStory(context: Context) = viewModelScope.launch {
        HomeStoryResult= HomeStoryRepo().HomeStory(context)
        Log.i("post", "onActivityResult:"+HomeStoryResult)
    }
}