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
import com.example.connect.model.Profile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class OthersProfilePostRepo{
    private val OthersProfilePostLiveData= MutableLiveData<Response<List<OthersPost>>>()
   suspend fun OthersPostProfile(user_id:Int?,context:Context): MutableLiveData<Response<List<OthersPost>>> {
        Log.i("user_id","$user_id")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).viewOtherPost(
            Profile(
                user_id=user_id
            )
        )

        OthersProfilePostLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<OthersPost>?> {
            override fun onResponse(
                call: Call<List<OthersPost>?>,
                response: retrofit2.Response<List<OthersPost>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    OthersProfilePostLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofilepost", "onActivityResult:"+responseBody)
                } else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        OthersPostProfile(user_id,context)
                    }
                }
                else {
                    OthersProfilePostLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofilepost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<OthersPost>?>, t: Throwable) {
                OthersProfilePostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofilepost", "onActivityResult: failed" )
            }
        })
        return OthersProfilePostLiveData
    }
}