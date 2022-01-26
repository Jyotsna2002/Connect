package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import retrofit2.Call
import retrofit2.Callback

class ProfilePhotoRepo(private val service: ApiInterface) {
    private val OthersProfileLiveData= MutableLiveData<Response<OthersPost>>()
    fun Profile(): MutableLiveData<Response<OthersPost>> {
        val call=service.profile()
        OthersProfileLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<OthersPost?> {
            override fun onResponse(
                call: Call<OthersPost?>,
                response: retrofit2.Response<OthersPost?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    OthersProfileLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofile", "onActivityResult:"+responseBody)
                }
                else {
                    OthersProfileLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofile", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<OthersPost?>, t: Throwable) {
                OthersProfileLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofile", "onActivityResult: failed" )
            }
        })
        return OthersProfileLiveData
    }
}