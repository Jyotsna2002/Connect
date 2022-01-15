package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Network.ServiceBuilder2
import com.example.connect.model.AuthDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class CreatePasswordRepo {
    private val passwordLiveData= MutableLiveData<Response<ResponseBody>>()
    var userData = MutableLiveData<AuthDataClass>()
    val passwordResponse: LiveData<Response<ResponseBody>>
        get()=passwordLiveData

    fun passwordApi(email:String,name:String,password:String) {

        val request = ServiceBuilder2.buildService()
        val call = request.createPassword(
            AuthDataClass(
                email = email,
                name = name,
                password = password
            )
        )
        passwordLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    passwordLiveData.postValue(Response.Success(response.body()))

                } else {
                    passwordLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                passwordLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
            }
        })

    }
    fun ForgetPassword(email:String,password:String){
        val request = ServiceBuilder2.buildService()
        val call = request.ForgetPassword(
            AuthDataClass(
                email = email,
                new_password = password
            )
        )
        passwordLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {

                    passwordLiveData.postValue(Response.Success(response.body()))

                }
                else if(response.code()==403)
                {
                    passwordLiveData.postValue(Response.Error("User is not registered"))
                }
                else if(response.code()==400)
                {
                    passwordLiveData.postValue(Response.Error("Password can not be same as old password"))
                }
                else {
                    passwordLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                passwordLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
            }
        })

    }
}