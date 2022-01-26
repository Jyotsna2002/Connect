package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.*
import com.example.connect.Views.Dashboard.Post_Fragment
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.HomeStoryDataClass
import com.example.connect.model.OthersPost
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.ResponseBody

class ProfileViewModel(private val homeStoryRepo: ProfilePhotoRepo) : ViewModel()  {
    private var HomeStoryResult: MutableLiveData<Response<OthersPost>> = MutableLiveData()
    val homeProfileResult: LiveData<Response<OthersPost>>
        get() = HomeStoryResult
    fun HomeProfile() = viewModelScope.launch {
        HomeStoryResult= homeStoryRepo.Profile()
        Log.i("post", "onActivityResult:"+HomeStoryResult)
    }
}