package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.Profile
import retrofit2.Call
import retrofit2.Callback

class OthersProfileRepo(private val service: ApiInterface) {
    private val OthersProfileLiveData= MutableLiveData<Response<Profile>>()
    fun OthersProfile(user_id:Int?): MutableLiveData<Response<Profile>> {
        Log.i("user_id","$user_id")
        val call=service.viewProfile(
            Profile(
                user_id=user_id
            )
        )
        OthersProfileLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<Profile?> {
            override fun onResponse(
                call: Call<Profile?>,
                response: retrofit2.Response<Profile?>
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

            override fun onFailure(call: Call<Profile?>, t: Throwable) {
                OthersProfileLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofile", "onActivityResult: failed" )
            }
        })
        return OthersProfileLiveData
    }
}