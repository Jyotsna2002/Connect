package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder2
import com.example.connect.Password_check.Response
import com.example.connect.model.AuthDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class ForgetPasswordRepo {
    private val VerifyLiveData= MutableLiveData<Response<ResponseBody>>()
    val VerifyResponse: LiveData<Response<ResponseBody>>
        get()=VerifyLiveData

    fun verifyApi(email:String) {

        val request = ServiceBuilder2.buildService()
        val call = request.Verify(
            AuthDataClass(
                email = email,
            )
        )
        VerifyLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    VerifyLiveData.postValue(Response.Success(response.body()))

                } else if (response.code() == 400) {

                    VerifyLiveData.postValue(Response.Error("No such account exists"))
                }else if (response.code() == 406) {

                    VerifyLiveData.postValue(Response.Error("Access permission denied"))
                }
                else {
                    VerifyLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                VerifyLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
            }
        })

    }
}