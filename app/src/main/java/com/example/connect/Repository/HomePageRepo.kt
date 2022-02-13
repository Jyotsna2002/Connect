package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Dashboard
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.HomeDataClassItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback

class HomePageRepo {



    private val ShowPostLiveData= MutableLiveData<Response<List<HomeDataClassItem>>>()

   suspend fun ShowPost(context: Context): MutableLiveData<Response<List<HomeDataClassItem>>> {
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).showPost()
        ShowPostLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<HomeDataClassItem>?> {
                    override fun onResponse(
                        call: Call<List<HomeDataClassItem>?>,
                        response: retrofit2.Response<List<HomeDataClassItem>?>
                    ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowPostLiveData.postValue(Response.Success(responseBody))

                    Log.i("Hellopostsuccess", "onActivityResult:"+responseBody)
                }
                else if( response.code()==406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        ShowPost(context)
                    }
                }
                else {
                   ShowPostLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloposterror", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<HomeDataClassItem>?>, t: Throwable) {
                ShowPostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellopost", "onActivityResult: failed" )
            }
        })
        return ShowPostLiveData
    }
}


