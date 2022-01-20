package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.OthersPost
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import retrofit2.Call
import retrofit2.Callback

class SeeTagPostRepo(private val service: ApiInterface) {
    private val SeeTagLiveData= MutableLiveData<Response<List<OthersPost>>>()
    fun SeeTag(Tag:String?): MutableLiveData<Response<List<OthersPost>>> {
        Log.i("user_id", "Respone $Tag")
        val call=service.seeTag(
            OthersPost(
               tag=Tag
            )
        )
        SeeTagLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<OthersPost>?> {
            override fun onResponse(
                call: Call<List<OthersPost>?>,
                response: retrofit2.Response<List<OthersPost>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    SeeTagLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }
                else {
                    SeeTagLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<OthersPost>?>, t: Throwable) {
                SeeTagLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return SeeTagLiveData
    }
}