package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.Profile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class OthersProfileRepo {
    private val OthersProfileLiveData= MutableLiveData<Response<Profile>>()
   suspend fun OthersProfile(user_id:Int?,context:Context): MutableLiveData<Response<Profile>> {
        Log.i("user_idprofile","$user_id")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).viewProfile(
            Profile(
                user_id=user_id
            )
        )

        OthersProfileLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<Profile?> {
            override fun onResponse(
                call: Call<Profile?>,
                response: retrofit2.Response<Profile?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    OthersProfileLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofile", "onActivityResult:"+responseBody)
                }else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        OthersProfile(user_id,context)
                    }
                }
                else {
                    OthersProfileLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofile", "onActivityResult:"+response.code())
                }
            }

            override fun onFailure(call: Call<Profile?>, t: Throwable) {
                OthersProfileLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofile", "onActivityResult: failed" )
            }
        })
        return OthersProfileLiveData
    }
}