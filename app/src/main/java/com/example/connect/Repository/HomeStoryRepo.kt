package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.HomeStoryDataClass
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class HomeStoryRepo {

    private val HomeStoryLiveData= MutableLiveData<Response<List<HomeStoryDataClass>>>()

    suspend fun HomeStory(context: Context): MutableLiveData<Response<List<HomeStoryDataClass>>> {
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).homeStory()
        HomeStoryLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<HomeStoryDataClass>?> {
                    override fun onResponse(
                        call: Call<List<HomeStoryDataClass>?>,
                        response: retrofit2.Response<List<HomeStoryDataClass>?>
                    ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    HomeStoryLiveData.postValue(Response.Success(responseBody))

                    Log.i("Hellopost", "onActivityResult:"+responseBody)
                } else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        HomeStory(context)
                    }
                }
                else {
                    HomeStoryLiveData.postValue(Response.Error(response.message()))

                    Log.i("Hellopost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<HomeStoryDataClass>?>, t: Throwable) {
                HomeStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellopost", "onActivityResult: failed" )
            }
        })
        return HomeStoryLiveData
    }
}


