package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.HomePageRepo
import com.example.connect.Repository.HomeStoryRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Views.Dashboard.Post_Fragment
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.HomeStoryDataClass
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.ResponseBody

class HomeStoryViewModel(private val homeStoryRepo: HomeStoryRepo) : ViewModel()  {
    private var HomeStoryResult: MutableLiveData<Response<List<HomeStoryDataClass>>> = MutableLiveData()
    val homeStoryResult: LiveData<Response<List<HomeStoryDataClass>>>
        get() = HomeStoryResult
    fun HomeStory() = viewModelScope.launch {
        HomeStoryResult= homeStoryRepo.HomeStory()
        Log.i("post", "onActivityResult:"+HomeStoryResult)
    }
}