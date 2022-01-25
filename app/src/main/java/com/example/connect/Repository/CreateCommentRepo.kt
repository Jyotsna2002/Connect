package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.CommentDataClass
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import retrofit2.Call
import retrofit2.Callback

class CreateCommentRepo(private val service: ApiInterface) {
    private val CreateCommentLiveData= MutableLiveData<Response<CommentDataClass>>()
    fun CreateComment(post:Int?,parent:Int?,content:String?): MutableLiveData<Response<CommentDataClass>> {
        Log.i("user_id", "Respone $post $parent $content")
        val call=service.createComment(
            CommentDataClass(
               post = post,
                parent = parent,
                content = content
            )
        )
        CreateCommentLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<CommentDataClass?> {
            override fun onResponse(
                call: Call<CommentDataClass?>,
                response: retrofit2.Response<CommentDataClass?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    CreateCommentLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }
                else {
                    CreateCommentLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<CommentDataClass?>, t: Throwable) {
                CreateCommentLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return CreateCommentLiveData
    }
}