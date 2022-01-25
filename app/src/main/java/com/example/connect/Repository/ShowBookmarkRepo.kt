package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import retrofit2.Call
import retrofit2.Callback

class ShowBookmarkRepo(private val service: ApiInterface) {
    private val ShowBookmarkLiveData= MutableLiveData<Response<List<OthersPost>>>()
    fun ShowBookmark(): MutableLiveData<Response<List<OthersPost>>> {
        val call=service.showBookMark()
        ShowBookmarkLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<OthersPost>?> {
            override fun onResponse(
                call: Call<List<OthersPost>?>,
                response: retrofit2.Response<List<OthersPost>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowBookmarkLiveData.postValue(Response.Success(responseBody))

                    Log.i("Helloprofilepost", "onActivityResult:"+responseBody)
                }
                else {
                    ShowBookmarkLiveData.postValue(Response.Error(response.message()))

                    Log.i("Helloprofilepost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<OthersPost>?>, t: Throwable) {
                ShowBookmarkLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Helloprofilepost", "onActivityResult: failed" )
            }
        })
        return ShowBookmarkLiveData
    }
}