package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.CommentDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class ShowCommentRepo{
    private val ShowCommentLiveData= MutableLiveData<Response<List<CommentDataClass>>>()
   suspend fun ShowComment(post:Int?,context:Context): MutableLiveData<Response<List<CommentDataClass>>> {
        Log.i("user_id", "Respone $post")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).showComment(
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
                }else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        ShowComment(post,context)
                    }
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