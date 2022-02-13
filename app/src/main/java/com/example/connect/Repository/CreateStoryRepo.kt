package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.CreateStoryDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class CreateStoryRepo {
    private val CreateStoryLiveData= MutableLiveData<Response<CreateStoryDataClass>>()

   suspend fun  CreateStory(photos:List<String>?,videos:List<String>?,context: Context): MutableLiveData<Response<CreateStoryDataClass>> {

        Log.i("media", "media:$photos$videos")
       val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
       val call= ServiceBuilder1.buildService(token).createStory(
           CreateStoryDataClass(
               photos = photos,
               videos = videos,
           )
       )

        CreateStoryLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<CreateStoryDataClass?> {
            override fun onResponse(
                call: Call<CreateStoryDataClass?>,
                response: retrofit2.Response<CreateStoryDataClass?>
            ) {
                if (response.isSuccessful) {
                    CreateStoryLiveData.postValue(Response.Success(response.body()))
                    Log.i("HellosuccesStory", "onActivityResult: Success" )
                }
                else if (response.code()==400)
                {
                    CreateStoryLiveData.postValue(Response.Error("Choose am image to create your story"))
                }
                else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        CreateStory(photos,videos,context)
                    }
                }
                else {
                    CreateStoryLiveData.postValue(Response.Error(response.message()))
                    Log.i("HellosuccesStory", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<CreateStoryDataClass?>, t: Throwable) {
                CreateStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("HellosuccesStory", "onActivityResult: failed" )
            }
        })

        return CreateStoryLiveData
    }
    }
