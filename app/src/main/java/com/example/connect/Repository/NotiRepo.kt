package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.Notificationpage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class NotiRepo{
    private val ShowBookmarkLiveData= MutableLiveData<Response<List<Notificationpage>>>()
  suspend fun ShowNoti(context:Context): MutableLiveData<Response<List<Notificationpage>>> {
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).noti()
        ShowBookmarkLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<Notificationpage>?> {
            override fun onResponse(
                call: Call<List<Notificationpage>?>,
                response: retrofit2.Response<List<Notificationpage>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowBookmarkLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofilepost", "onActivityResult:"+responseBody)
                } else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        ShowNoti(context)
                    }
                }
                else {
                    ShowBookmarkLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofilepost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<Notificationpage>?>, t: Throwable) {
                ShowBookmarkLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofilepost", "onActivityResult: failed" )
            }
        })
        return ShowBookmarkLiveData
    }
}