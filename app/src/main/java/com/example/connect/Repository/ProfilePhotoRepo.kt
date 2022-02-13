package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.OthersPost
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class ProfilePhotoRepo{
    private val OthersProfileLiveData= MutableLiveData<Response<OthersPost>>()
   suspend fun Profile(context:Context): MutableLiveData<Response<OthersPost>> {
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).profile()
        OthersProfileLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<OthersPost?> {
            override fun onResponse(
                call: Call<OthersPost?>,
                response: retrofit2.Response<OthersPost?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    OthersProfileLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofile", "onActivityResult:"+responseBody)
                }else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        Profile(context)
                    }
                }
                else {
                    OthersProfileLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofile", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<OthersPost?>, t: Throwable) {
                OthersProfileLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofile", "onActivityResult: failed" )
            }
        })
        return OthersProfileLiveData
    }
}