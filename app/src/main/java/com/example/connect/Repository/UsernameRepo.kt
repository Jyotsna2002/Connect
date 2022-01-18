package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder2
import com.example.connect.model.AuthDataClass
import retrofit2.Call
import retrofit2.Callback

class UsernameRepo {
    private val UsernameLiveData= MutableLiveData<Response<AuthDataClass>>()
    var userDetails = MutableLiveData<AuthDataClass>()
    val UsernameResponse: MutableLiveData<Response<AuthDataClass>>
        get()=UsernameLiveData
    fun Createusername(username:String){
        val request = ServiceBuilder2.buildService()
        val call = request.createUsername(
            AuthDataClass(
             username=username
            )
        )
        UsernameLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<AuthDataClass?> {
            override fun onResponse(
                call: Call<AuthDataClass?>,
                response: retrofit2.Response<AuthDataClass?>
            ) {
                if (response.isSuccessful) {

                    UsernameLiveData.postValue(Response.Success(response.body()))
                    Log.d("RESPONSE BODY", response.body().toString())
                    userDetails.value=response.body()


                }
                else if(response.code()==406)
                {
                    UsernameLiveData.postValue(Response.Error("Username Already exist"))
                }
                else {
                    UsernameLiveData.postValue(Response.Error(response.message()))

                }
            }

            override fun onFailure(call: Call<AuthDataClass?>, t: Throwable) {
                UsernameLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
            }
        })

    }
}