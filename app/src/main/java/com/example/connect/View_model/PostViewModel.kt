package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Views.Dashboard.Post_Fragment.Companion.ok
import com.example.connect.model.PostDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class PostViewModel(private val postRepo: UploadPostRepo) : ViewModel() {

    var imageUrl = MutableLiveData<List<String>>()
    var videoUrl = MutableLiveData<List<String>>()
    var caption =  MutableLiveData<String>()

    private var postResult:MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val Result:LiveData<Response<ResponseBody>>
        get() = postResult

    fun submitData() = viewModelScope.launch {
        if(ok=="0")
        {
            imageUrl.value= emptyList()
        }
        else if(ok=="1"){
            videoUrl.value= emptyList()
        }
        else
        {
            caption.value=""
        }
        postResult= postRepo.uploadmedia(imageUrl.value,videoUrl.value,caption.value) as MutableLiveData<Response<ResponseBody>>
    }
}