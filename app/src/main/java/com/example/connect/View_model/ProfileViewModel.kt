package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.*
import com.example.connect.model.OthersPost
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel()  {
    private var HomeStoryResult: MutableLiveData<Response<OthersPost>> = MutableLiveData()
    val homeProfileResult: LiveData<Response<OthersPost>>
        get() = HomeStoryResult
    fun HomeProfile(context: Context) = viewModelScope.launch {
        HomeStoryResult= ProfilePhotoRepo().Profile(context)
        Log.i("post", "onActivityResult:"+HomeStoryResult)
    }
}