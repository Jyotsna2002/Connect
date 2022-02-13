package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.LikePostDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class LikePostRepo {
    private val LikeStoryLiveData= MutableLiveData<Response<LikePostDataClass>>()

  suspend  fun  LikePost(PostId:Int?,context: Context): MutableLiveData<Response<LikePostDataClass>> {

        Log.i("media", "media:$PostId")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).likePost(
            LikePostDataClass(
                post_id = PostId
            )
        )

        LikeStoryLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<LikePostDataClass?> {
            override fun onResponse(
                call: Call<LikePostDataClass?>,
                response: retrofit2.Response<LikePostDataClass?>
            ) {
                if (response.isSuccessful) {
                    LikeStoryLiveData.postValue(Response.Success(response.body()))
                    Log.i("HellosuccesStory", "onActivityResult: Success" )
                }
                else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        LikePost(PostId,context)
                    }
                }
                else {
                    LikeStoryLiveData.postValue(Response.Error(response.message()))
                    Log.i("HellosuccesStory", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<LikePostDataClass?>, t: Throwable) {
                LikeStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("HellosuccesStory", "onActivityResult: failed" )
            }
        })

        return LikeStoryLiveData
    }
    }
