package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.CommentDataClass
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import retrofit2.Call
import retrofit2.Callback

class ShowCommentRepo(private val service: ApiInterface) {
    private val ShowCommentLiveData= MutableLiveData<Response<List<CommentDataClass>>>()
    fun ShowComment(post:Int?): MutableLiveData<Response<List<CommentDataClass>>> {
        Log.i("user_id", "Respone $post")
        val call=service.showComment(
            CommentDataClass(
               post = post
            )
        )
        ShowCommentLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<CommentDataClass>?> {
            override fun onResponse(
                call: Call<List<CommentDataClass>?>,
                response: retrofit2.Response<List<CommentDataClass>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowCommentLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }
                else {
                    ShowCommentLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<CommentDataClass>?>, t: Throwable) {
                ShowCommentLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return ShowCommentLiveData
    }
}