package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.CommentDataClass
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import retrofit2.Call
import retrofit2.Callback

class CreateBookmarkRepo(private val service: ApiInterface) {
    private val CreateBookmarkLiveData= MutableLiveData<Response<HomeDataClassItem>>()
    fun CreateBookmark(post:Int?): MutableLiveData<Response<HomeDataClassItem>> {
        Log.i("user_id", "Respone $post")
        val call=service.createBookmark(
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