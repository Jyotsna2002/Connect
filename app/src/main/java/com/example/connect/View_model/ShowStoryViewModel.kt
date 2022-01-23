package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.EditProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.ShowStoryRepo
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Views.Dashboard.Post_Fragment.Companion.choose
import com.example.connect.Views.Dashboard.Post_Fragment.Companion.ok
import com.example.connect.model.PostDataClass
import com.example.connect.model.ShowStoryDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class ShowStoryViewModel(private val ShowStoryRepo: ShowStoryRepo) : ViewModel() {

    var UserId = MutableLiveData<Int>()

    private var ShowStoryResult:MutableLiveData<Response<List<ShowStoryDataClass>>> = MutableLiveData()
    val showStoryResult:LiveData<Response<List<ShowStoryDataClass>>>
        get() = ShowStoryResult

    fun ShowStorySubmitData() = viewModelScope.launch {
        ShowStoryResult =ShowStoryRepo.ShowStory(UserId.value)

    }
}
