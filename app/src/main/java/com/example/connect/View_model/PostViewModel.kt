package com.example.connect.View_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.UploadPostRepo
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class PostViewModel : ViewModel() {

    var imageUrl = MutableLiveData<List<String>>()
    var videoUrl = MutableLiveData<List<String>>()
    var caption =  MutableLiveData<String>()

    private var postResult:MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val Result:LiveData<Response<ResponseBody>>
        get() = postResult

    fun submitData(context: Context) = viewModelScope.launch {
//        if(ok=="0")
//        {
//            imageUrl.value= emptyList()
//        }
//        else if(ok=="1")
//        {
//            videoUrl.value= emptyList()
//        }

//        if (choose==1) {
//            postResult = postRepo.uploadmedia(
//                image,
//                videoUrl.value,
//                caption.value
//            ) as MutableLiveData<Response<ResponseBody>>
//        }
//        else
//            if(choose==0)
//            {
                postResult = UploadPostRepo().uploadmedia(
                    imageUrl.value,
                    emptyList(),
                    caption.value,context
                ) as MutableLiveData<Response<ResponseBody>>
//            }
    }
}
