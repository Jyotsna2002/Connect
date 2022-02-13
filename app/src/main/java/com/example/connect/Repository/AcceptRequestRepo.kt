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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class AcceptRequestRepo {
    private val AcceptRequestLiveData= MutableLiveData<Response<ResponseBody>>()
   suspend fun AcceptRequest(followId:Int?,confirm:String?,context:Context): MutableLiveData<Response<ResponseBody>> {
        Log.i("user_id", "Respone $followId$confirm")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).acceptRequest(
            ShowFollowRequestDataClass(
                follow_id=followId,
                confirm = confirm
            )
        )

        AcceptRequestLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    AcceptRequestLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                } else if( response.code() == 403 || response.code()==400 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        AcceptRequest(followId,confirm,context)
                    }
                }
                else {
                    AcceptRequestLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                AcceptRequestLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return AcceptRequestLiveData
    }
}