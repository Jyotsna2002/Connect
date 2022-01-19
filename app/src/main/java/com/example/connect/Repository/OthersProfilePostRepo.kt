package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import retrofit2.Call
import retrofit2.Callback

class OthersProfilePostRepo(private val service: ApiInterface) {
    private val OthersProfilePostLiveData= MutableLiveData<Response<List<OthersPost>>>()
    fun OthersPostProfile(user_id:Int?): MutableLiveData<Response<List<OthersPost>>> {
        Log.i("user_id","$user_id")
        val call=service.viewOtherPost(
            Profile(
                user_id=user_id
            )
        )
        OthersProfilePostLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<OthersPost>?> {
            override fun onResponse(
                call: Call<List<OthersPost>?>,
                response: retrofit2.Response<List<OthersPost>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    OthersProfilePostLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofilepost", "onActivityResult:"+responseBody)
                }
                else {
                    OthersProfilePostLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofilepost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<OthersPost>?>, t: Throwable) {
                OthersProfilePostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofilepost", "onActivityResult: failed" )
            }
        })
        return OthersProfilePostLiveData
    }
}