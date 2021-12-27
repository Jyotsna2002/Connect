package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class SignUpRepo {
    private val signUpLiveData= MutableLiveData<Response<ResponseBody>>()
    val signUPResponse: LiveData<Response<ResponseBody>>
        get()=signUpLiveData

    fun signUpApi(email:String,name:String) {

        val request = ServiceBuilder1.buildService()
        val call = request.signup(
            AuthDataClass(
                email = email,
                name = name
            )
        )
        signUpLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    signUpLiveData.postValue(Response.Success(response.body()))

                } else {
                    signUpLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                signUpLiveData.postValue(Response.Error("Something went wrong"))
            }
        })

    }
}