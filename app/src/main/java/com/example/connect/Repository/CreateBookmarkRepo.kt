package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.HomeDataClassItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class CreateBookmarkRepo{
    private val CreateBookmarkLiveData= MutableLiveData<Response<HomeDataClassItem>>()
   suspend fun CreateBookmark(post:Int?,context:Context): MutableLiveData<Response<HomeDataClassItem>> {
        Log.i("user_id", "Respone $post")
       val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
       val call= ServiceBuilder1.buildService(token).createBookmark(
           HomeDataClassItem(
               post_id = post
           )
       )

        CreateBookmarkLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<HomeDataClassItem?> {
            override fun onResponse(
                call: Call<HomeDataClassItem?>,
                response: retrofit2.Response<HomeDataClassItem?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    CreateBookmarkLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                } else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        CreateBookmark(post,context)
                    }
                }
                else {
                    CreateBookmarkLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<HomeDataClassItem?>, t: Throwable) {
                CreateBookmarkLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return CreateBookmarkLiveData
    }
}