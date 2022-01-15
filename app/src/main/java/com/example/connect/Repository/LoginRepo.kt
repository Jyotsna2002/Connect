package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Network.ServiceBuilder2
import com.example.connect.model.AuthDataClass
import retrofit2.Call
import retrofit2.Callback

class LoginRepo {

   private val loginLiveData= MutableLiveData<Response<AuthDataClass>>()
   var userDetails = MutableLiveData<AuthDataClass>()
   val loginResponse: MutableLiveData<Response<AuthDataClass>>
   get()=loginLiveData

   fun loginApi(email:String,password:String) {

      val request = ServiceBuilder2.buildService()
      val call = request.login(
         AuthDataClass(
            email = email,
            password = password
         )
      )
      loginLiveData.postValue(Response.Loading())
         call.enqueue(object : Callback<AuthDataClass?> {
            override fun onResponse(
                call: Call<AuthDataClass?>,
                response: retrofit2.Response<AuthDataClass?>
            ) {
               if (response.isSuccessful) {

                  loginLiveData.postValue(Response.Success(response.body()))
                  Log.d("RESPONSE BODY", response.body().toString())
                  userDetails.value=response.body()

               } else if (response.code() == 401) {

                  loginLiveData.postValue(Response.Error("User not registered"))
               }
                else if(response.code()==400)
               {
                  loginLiveData.postValue(Response.Error("Wrong Password"))
               }
               else {
                  loginLiveData.postValue(Response.Error(response.message()))

               }
            }

            override fun onFailure(call: Call<AuthDataClass?>, t: Throwable) {
               loginLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
            }
         })

   }
}