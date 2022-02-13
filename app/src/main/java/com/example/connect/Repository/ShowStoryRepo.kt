package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.ShowStoryDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class ShowStoryRepo{
    private val ShowStoryLiveData= MutableLiveData<Response<List<ShowStoryDataClass>>>()

   suspend fun ShowStory(UserId:Int?,context:Context): MutableLiveData<Response<List<ShowStoryDataClass>>> {

        Log.i("media", "media:$UserId")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).showStory(
            ShowStoryDataClass(
                user_id = UserId
            )
        )

        ShowStoryLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<ShowStoryDataClass>?> {
            override fun onResponse(
                call: Call<List<ShowStoryDataClass>?>,
                response: retrofit2.Response<List<ShowStoryDataClass>?>
            ) {
                if (response.isSuccessful) {
                    ShowStoryLiveData.postValue(Response.Success(response.body()))
                    Log.i("HellosuccesStory", "onActivityResult: Success" )
                }
                else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        ShowStory(UserId,context)
                    }
                }
                else {
                    ShowStoryLiveData.postValue(Response.Error(response.message()))
                    Log.i("HellosuccesStory", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<ShowStoryDataClass>?>, t: Throwable) {
                ShowStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("HellosuccesStory", "onActivityResult: failed" )
            }
        })

        return ShowStoryLiveData
    }
    }
