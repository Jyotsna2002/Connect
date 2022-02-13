package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.PostDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UploadPostRepo{
    private val uploadPostLiveData= MutableLiveData<Response<ResponseBody>>()
    val uploadPostResponse: LiveData<Response<ResponseBody>>
        get()=uploadPostLiveData

   suspend fun uploadmedia(photos:List<String>?,videos:List<String>?,captions:String?,context:Context): LiveData<Response<ResponseBody>> {

        Log.i("media", "media:$photos$videos$captions")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).uploadpost(
            PostDataClass(
                photos = photos,
                videos = videos,
                caption = captions
            )
        )

       // Log.i("media", "media:$call")
        uploadPostLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    uploadPostLiveData.postValue(Response.Success(response.body()))
                   // Toast.makeText(context,"Post Successfully Created",Toast.LENGTH_SHORT).show()
                 //   Log.i("Hellosucces", "onActivityResult: Success" )
                }
                else if (response.code()==400)
                {
                    uploadPostLiveData.postValue(Response.Error("Choose am image to post"))
                }else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        uploadmedia(photos,videos,captions,context)
                    }
                }
                else {
                    uploadPostLiveData.postValue(Response.Error(response.message()))
                   // Log.i("Hellosucces", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadPostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
              //  Log.i("Hellosucces", "onActivityResult: failed" )
            }
        })

        return uploadPostResponse
    }
    }
