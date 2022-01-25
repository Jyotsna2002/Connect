package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.Notificationpage
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import retrofit2.Call
import retrofit2.Callback

class NotiRepo(private val service: ApiInterface) {
    private val ShowBookmarkLiveData= MutableLiveData<Response<List<Notificationpage>>>()
    fun ShowNoti(): MutableLiveData<Response<List<Notificationpage>>> {
        val call=service.noti()
        ShowBookmarkLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<Notificationpage>?> {
            override fun onResponse(
                call: Call<List<Notificationpage>?>,
                response: retrofit2.Response<List<Notificationpage>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowBookmarkLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofilepost", "onActivityResult:"+responseBody)
                }
                else {
                    ShowBookmarkLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofilepost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<Notificationpage>?>, t: Throwable) {
                ShowBookmarkLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofilepost", "onActivityResult: failed" )
            }
        })
        return ShowBookmarkLiveData
    }
}