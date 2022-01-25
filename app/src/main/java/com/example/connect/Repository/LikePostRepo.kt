package com.example.connect.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.CreateStoryDataClass
import com.example.connect.model.LikePostDataClass
import com.example.connect.model.PostDataClass
import com.example.connect.model.ShowStoryDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.coroutineContext

class LikePostRepo(private val service:ApiInterface) {
    private val LikeStoryLiveData= MutableLiveData<Response<LikePostDataClass>>()

    fun  LikePost(PostId:Int?): MutableLiveData<Response<LikePostDataClass>> {

        Log.i("media", "media:$PostId")
        val call = service.likePost(
            LikePostDataClass(
                post_id = PostId
            )
        )
        LikeStoryLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<LikePostDataClass?> {
            override fun onResponse(
                call: Call<LikePostDataClass?>,
                response: retrofit2.Response<LikePostDataClass?>
            ) {
                if (response.isSuccessful) {
                    LikeStoryLiveData.postValue(Response.Success(response.body()))
                    Log.i("HellosuccesStory", "onActivityResult: Success" )
                }

                else {
                    LikeStoryLiveData.postValue(Response.Error(response.message()))
                    Log.i("HellosuccesStory", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<LikePostDataClass?>, t: Throwable) {
                LikeStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("HellosuccesStory", "onActivityResult: failed" )
            }
        })

        return LikeStoryLiveData
    }
    }
