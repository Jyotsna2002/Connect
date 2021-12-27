package com.example.connect.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class LoginRepo {

   private val loginLiveData= MutableLiveData<Response<ResponseBody>>()
   val loginResponse: LiveData<Response<ResponseBody>>
   get()=loginLiveData

   fun loginApi(email:String,password:String) {

      val request = ServiceBuilder1.buildService()
      val call = request.login(
         AuthDataClass(
            email = email,
            password = password
         )
      )
      loginLiveData.postValue(Response.Loading())
         call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
               call: Call<ResponseBody?>,
               response: retrofit2.Response<ResponseBody?>
            ) {
               if (response.isSuccessful) {

                  loginLiveData.postValue(Response.Success(response.body()))

               } else if (response.code() == 401) {

                  loginLiveData.postValue(Response.Error("User not registered"))
               } else {
                  loginLiveData.postValue(Response.Error(response.message()))
               }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               loginLiveData.postValue(Response.Error("Something went wrong"))
            }
         })

   }
}