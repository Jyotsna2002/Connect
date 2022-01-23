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
import com.example.connect.model.HomeStoryDataClass
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import retrofit2.Call
import retrofit2.Callback

class HomeStoryRepo(private val service: ApiInterface) {



    private val HomeStoryLiveData= MutableLiveData<Response<List<HomeStoryDataClass>>>()

    fun HomeStory(): MutableLiveData<Response<List<HomeStoryDataClass>>> {
        val call=service.homeStory()
        HomeStoryLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<HomeStoryDataClass>?> {
                    override fun onResponse(
                        call: Call<List<HomeStoryDataClass>?>,
                        response: retrofit2.Response<List<HomeStoryDataClass>?>
                    ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    HomeStoryLiveData.postValue(Response.Success(responseBody))

                    Log.i("Hellopost", "onActivityResult:"+responseBody)
                }
                else {
                    HomeStoryLiveData.postValue(Response.Error(response.message()))

                    Log.i("Hellopost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<HomeStoryDataClass>?>, t: Throwable) {
                HomeStoryLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellopost", "onActivityResult: failed" )
            }
        })
        return HomeStoryLiveData
    }
}


