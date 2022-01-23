package com.example.connect.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.CreateStoryDataClass
import com.example.connect.model.PostDataClass
import com.example.connect.model.ShowStoryDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.coroutineContext

class ShowStoryRepo(private val service:ApiInterface) {
    private val ShowStoryLiveData= MutableLiveData<Response<List<ShowStoryDataClass>>>()

    fun  ShowStory(UserId:Int?): MutableLiveData<Response<List<ShowStoryDataClass>>> {

        Log.i("media", "media:$UserId")
        val call = service.showStory(
            ShowStoryDataClass(
                user_id = UserId
            )
        )
        ShowStoryLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<ShowStoryDataClass>?> {
            override fun onResponse(
                call: Call<List<ShowStoryDataClass>?>,
                response: retrofit2.Response<List<ShowStoryDataClass>?>
            ) {
                if (response.isSuccessful) {
                    ShowStoryLiveData.postValue(Response.Success(response.body()))
                    Log.i("HellosuccesStory", "onActivityResult: Success" )
                }

                else {
                    ShowStoryLiveData.postValue(Response.Error(response.message()))
                    Log.i("HellosuccesStory", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<ShowStoryDataClass>?>, t: Throwable) {
                ShowStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("HellosuccesStory", "onActivityResult: failed" )
            }
        })

        return ShowStoryLiveData
    }
    }
