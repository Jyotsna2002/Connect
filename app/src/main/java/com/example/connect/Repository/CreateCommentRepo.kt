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

class CreateCommentRepo{
    private val CreateCommentLiveData= MutableLiveData<Response<CommentDataClass>>()
   suspend fun CreateComment(post:Int?,parent:Int?,content:String?,context:Context): MutableLiveData<Response<CommentDataClass>> {
        Log.i("user_id", "Respone $post $parent $content")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).createComment(
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
                }  else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        CreateComment(post,parent,content,context)
                    }
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