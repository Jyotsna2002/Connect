package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.model.PostDataClass
import okhttp3.ResponseBody


class PostViewModel() : ViewModel() {

    var imageUrl = MutableLiveData<List<String>?>()
    var videoUrl = MutableLiveData<List<String>?>()

    fun submitData(): LiveData<Response<ResponseBody>> {
        val uploadPostRepo: UploadPostRepo = UploadPostRepo()
        imageUrl.value?.let { uploadPostRepo.uploadmedia(it, videoUrl.value, null) }
        val result = uploadPostRepo.uploadPostResponse
        return result
    }
}