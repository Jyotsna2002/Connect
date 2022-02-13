package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.ShowFollowRequestDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class ShowRequestPageRepo{



    private val ShowRequestLiveData= MutableLiveData<Response<List<ShowFollowRequestDataClass>>>()

   suspend fun ShowRequest(context:Context): MutableLiveData<Response<List<ShowFollowRequestDataClass>>> {
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).showRequest()
        ShowRequestLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<ShowFollowRequestDataClass>?> {
                    override fun onResponse(
                        call: Call<List<ShowFollowRequestDataClass>?>,
                        response: retrofit2.Response<List<ShowFollowRequestDataClass>?>
                    ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowRequestLiveData.postValue(Response.Success(responseBody))

                    Log.i("Hellorequest", "onActivityResult:$responseBody")
                }else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        ShowRequest(context)
                    }
                }
                else {
                    ShowRequestLiveData.postValue(Response.Error(response.message()))

                    Log.i("Hellorequest", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<ShowFollowRequestDataClass>?>, t: Throwable) {
                ShowRequestLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellorequest", "onActivityResult: failed" )
            }
        })
        return ShowRequestLiveData
    }
}


