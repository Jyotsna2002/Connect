package com.example.connect.Repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connect.Network.ApiInterface
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.ShowFollowRequestDataClass
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import retrofit2.Call
import retrofit2.Callback

class ShowRequestPageRepo(private val service: ApiInterface) {



    private val ShowRequestLiveData= MutableLiveData<Response<List<ShowFollowRequestDataClass>>>()

    fun ShowRequest(): MutableLiveData<Response<List<ShowFollowRequestDataClass>>> {
        val call=service.showRequest()
        ShowRequestLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<ShowFollowRequestDataClass>?> {
                    override fun onResponse(
                        call: Call<List<ShowFollowRequestDataClass>?>,
                        response: retrofit2.Response<List<ShowFollowRequestDataClass>?>
                    ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowRequestLiveData.postValue(Response.Success(responseBody))

                    Log.i("Hellorequest", "onActivityResult:$responseBody")
                }
                else {
                    ShowRequestLiveData.postValue(Response.Error(response.message()))

                    Log.i("Hellorequest", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<ShowFollowRequestDataClass>?>, t: Throwable) {
                ShowRequestLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellorequest", "onActivityResult: failed" )
            }
        })
        return ShowRequestLiveData
    }
}


