package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Network.ServiceBuilder2
import com.example.connect.model.AuthDataClass
import com.example.connect.model.PostDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UploadPostRepo {
    private val uploadPostLiveData= MutableLiveData<Response<ResponseBody>>()
    val uploadPostResponse: LiveData<Response<ResponseBody>>
        get()=uploadPostLiveData

    fun uploadmedia(photos:List<String>,videos:List<String>?,captions:String?){

        val request = ServiceBuilder2.buildService()
        val call = request.uploadpost(
            PostDataClass(
                photos = photos,
                videos = videos,
                caption = captions
            )
        )
        uploadPostLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    uploadPostLiveData.postValue(Response.Success(response.body()))

                }
                else {
                    uploadPostLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadPostLiveData.postValue(Response.Error("Something went wrong"))
            }
        })

    }
    }
