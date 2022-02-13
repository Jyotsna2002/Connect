package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.EditProfileDataClass
import com.example.connect.model.Profile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SendRequestRepo{
    private val SendRequestLiveData= MutableLiveData<Response<EditProfileDataClass>>()
   suspend fun SendRequest(user_id:Int?,context:Context): MutableLiveData<Response<EditProfileDataClass>> {
        Log.i("user_id","$user_id")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).sendRequest(
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
                } else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        SendRequest(user_id,context)
                    }
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