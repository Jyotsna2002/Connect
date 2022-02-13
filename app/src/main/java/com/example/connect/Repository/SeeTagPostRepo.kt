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

class SeeTagPostRepo{
    private val SeeTagLiveData= MutableLiveData<Response<List<OthersPost>>>()
   suspend fun SeeTag(Tag:String?,context:Context): MutableLiveData<Response<List<OthersPost>>> {
        Log.i("user_id", "Respone $Tag")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).seeTag(
            OthersPost(
                tag=Tag
            )
        )

        SeeTagLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<OthersPost>?> {
            override fun onResponse(
                call: Call<List<OthersPost>?>,
                response: retrofit2.Response<List<OthersPost>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    SeeTagLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        SeeTag(Tag,context)
                    }
                }
                else {
                    SeeTagLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<OthersPost>?>, t: Throwable) {
                SeeTagLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return SeeTagLiveData
    }
}