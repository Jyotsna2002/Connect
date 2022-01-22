package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.EditProfileDataClass
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class SendRequestRepo(private val service: ApiInterface) {
    private val SendRequestLiveData= MutableLiveData<Response<EditProfileDataClass>>()
    fun SendRequest(user_id:Int?): MutableLiveData<Response<EditProfileDataClass>> {
        Log.i("user_id","$user_id")
        val call=service.sendRequest(
            Profile(
                user_id=user_id
            )
        )
        SendRequestLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<EditProfileDataClass?> {
            override fun onResponse(
                call: Call<EditProfileDataClass?>,
                response: retrofit2.Response<EditProfileDataClass?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    SendRequestLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofilepost", "onActivityResult:"+responseBody)
                }
                else {
                    SendRequestLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofilepost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<EditProfileDataClass?>, t: Throwable) {
                SendRequestLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofilepost", "onActivityResult: failed"+t.message )
            }
        })
        return SendRequestLiveData
    }
}