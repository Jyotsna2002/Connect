package com.example.connect.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.CreateStoryDataClass
import com.example.connect.model.PostDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.coroutineContext

class CreateStoryRepo(private val service:ApiInterface) {
    private val CreateStoryLiveData= MutableLiveData<Response<CreateStoryDataClass>>()

    fun  CreateStory(photos:List<String>?,videos:List<String>?): MutableLiveData<Response<CreateStoryDataClass>> {

        Log.i("media", "media:$photos$videos")
        val call = service.createStory(
            CreateStoryDataClass(
                photos = photos,
                videos = videos,
            )
        )
        CreateStoryLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<CreateStoryDataClass?> {
            override fun onResponse(
                call: Call<CreateStoryDataClass?>,
                response: retrofit2.Response<CreateStoryDataClass?>
            ) {
                if (response.isSuccessful) {
                    CreateStoryLiveData.postValue(Response.Success(response.body()))
                    Log.i("HellosuccesStory", "onActivityResult: Success" )
                }
                else if (response.code()==400)
                {
                    CreateStoryLiveData.postValue(Response.Error("Choose am image to create your story"))
                }
                else {
                    CreateStoryLiveData.postValue(Response.Error(response.message()))
                    Log.i("HellosuccesStory", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<CreateStoryDataClass?>, t: Throwable) {
                CreateStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("HellosuccesStory", "onActivityResult: failed" )
            }
        })

        return CreateStoryLiveData
    }
    }
