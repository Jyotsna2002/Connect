package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import com.example.connect.model.ShowFollowRequestDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class AcceptRequestRepo(private val service: ApiInterface) {
    private val AcceptRequestLiveData= MutableLiveData<Response<ResponseBody>>()
    fun AcceptRequest(followId:Int?,confirm:String?): MutableLiveData<Response<ResponseBody>> {
        Log.i("user_id", "Respone $followId$confirm")
        val call=service.acceptRequest(
            ShowFollowRequestDataClass(
                follow_id=followId,
                confirm = confirm
            )
        )
        AcceptRequestLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    AcceptRequestLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }
                else {
                    AcceptRequestLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                AcceptRequestLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return AcceptRequestLiveData
    }
}