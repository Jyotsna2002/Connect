package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.View_model.AuthDataClass
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

                }
                else if(response.code()==400)
                {
                    otpLiveData.postValue(Response.Error("Your Otp is Wrong"))
                }
                else if(response.code()==403)
                {
                    otpLiveData.postValue(Response.Error("Your otp is expired, Resend Otp"))
                }
                else {
                    otpLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                otpLiveData.postValue(Response.Error("Something went wrong"))
            }
        })

    }
    fun EnterOtp(email:String,otp:String){
        val request = ServiceBuilder1.buildService()
        val call = request.EnterOtp(
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

                }
                else if(response.code()==408)
                {
                    otpLiveData.postValue(Response.Error("Your otp is expired, Resend Otp"))
                }
                else if(response.code()==409)
                {
                    otpLiveData.postValue(Response.Error("Your otp is Incorrect"))
                }
                else if(response.code()==401)
                {
                    otpLiveData.postValue(Response.Error("Your otp does not belong to your email"))
                }else {
                    otpLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                otpLiveData.postValue(Response.Error("Something went wrong"))
            }
        })
    }
}