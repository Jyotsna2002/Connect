package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder2
import com.example.connect.Password_check.Response
import com.example.connect.model.AuthDataClass
import retrofit2.Call
import retrofit2.Callback

class CreatePasswordRepo {
    private val passwordLiveData= MutableLiveData<Response<AuthDataClass>>()
    var userData = MutableLiveData<AuthDataClass>()
    val passwordResponse: LiveData<Response<AuthDataClass>>
        get()=passwordLiveData

    fun passwordApi(email:String,name:String,password:String,username:String) {

        val request = ServiceBuilder2.buildService()
        val call = request.createPassword(
            AuthDataClass(
                email = email,
                name = name,
                password = password,
                username=username
            )
        )
        passwordLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<AuthDataClass?> {
            override fun onResponse(
                call: Call<AuthDataClass?>,
                response: retrofit2.Response<AuthDataClass?>
            ) {
                if (response.isSuccessful) {

                    passwordLiveData.postValue(Response.Success(response.body()))
                    userData.value=response.body()

                } else {
                    passwordLiveData.postValue(Response.Error(response.code().toString()))
                }
            }

            override fun onFailure(call: Call<AuthDataClass?>, t: Throwable) {
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
        call.enqueue(object : Callback<AuthDataClass?> {
            override fun onResponse(
                call: Call<AuthDataClass?>,
                response: retrofit2.Response<AuthDataClass?>
            ) {
                if (response.isSuccessful) {

                    passwordLiveData.postValue(Response.Success(response.body()))
                    userData.value=response.body()
                }
                else if(response.code()==400)
                {
                    passwordLiveData.postValue(Response.Error("User is not registered"))
                }
                else if(response.code()==406)
                {
                    passwordLiveData.postValue(Response.Error("Password can not be same as old password"))
                }
                else {
                    passwordLiveData.postValue(Response.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<AuthDataClass?>, t: Throwable) {
                passwordLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
            }
        })

    }
}