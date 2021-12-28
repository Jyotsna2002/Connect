package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class OtpRepo {
    private val otpLiveData= MutableLiveData<Response<ResponseBody>>()
    val otpResponse: LiveData<Response<ResponseBody>>
        get()=otpLiveData

    fun otpApi(email:String,otp:String) {

        val request = ServiceBuilder1.buildService()
        val call = request.otp(
            AuthDataClass(
                email = email,
                otp = otp
            )
        )
        otpLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    otpLiveData.postValue(Response.Success(response.body()))

                } else {
                    otpLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                otpLiveData.postValue(Response.Error("Something went wrong"))
            }
        })

    }
}