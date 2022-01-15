package com.example.connect.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.PostDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.coroutineContext

class UploadPostRepo(private val service:ApiInterface) {
    private val uploadPostLiveData= MutableLiveData<Response<ResponseBody>>()
    val uploadPostResponse: LiveData<Response<ResponseBody>>
        get()=uploadPostLiveData

    fun uploadmedia(photos:List<String>?,videos:List<String>?,captions:String?): LiveData<Response<ResponseBody>> {

        Log.i("media", "media:$photos$videos$captions")
        val call = service.uploadpost(
            PostDataClass(
                photos = photos,
                videos = videos,
                caption = captions
            )
        )
       // Log.i("media", "media:$call")
        uploadPostLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    uploadPostLiveData.postValue(Response.Success(response.body()))
                   // Toast.makeText(context,"Post Successfully Created",Toast.LENGTH_SHORT).show()
                    Log.i("Hellosucces", "onActivityResult: Success" )
                }
                else {
                    uploadPostLiveData.postValue(Response.Error(response.message()))
                    Log.i("Hellosucces", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadPostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellosucces", "onActivityResult: failed" )
            }
        })

        return uploadPostResponse
    }
    }
