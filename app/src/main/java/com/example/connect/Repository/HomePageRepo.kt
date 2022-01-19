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
import com.example.connect.recylcer_view_adapter.HomePageAdapter
import retrofit2.Call
import retrofit2.Callback

class HomePageRepo(private val context: Context?,private val service: ApiInterface) {



    private val ShowPostLiveData= MutableLiveData<Response<List<HomeDataClassItem>>>()

    fun ShowPost(): MutableLiveData<Response<List<HomeDataClassItem>>> {
        val call=service.showPost()
        ShowPostLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<HomeDataClassItem>?> {
                    override fun onResponse(
                        call: Call<List<HomeDataClassItem>?>,
                        response: retrofit2.Response<List<HomeDataClassItem>?>
                    ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    ShowPostLiveData.postValue(Response.Success(responseBody))

                    Log.i("Hellopost", "onActivityResult:"+responseBody)
                }
                else {
                   ShowPostLiveData.postValue(Response.Error(response.message()))

                    Log.i("Hellopost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<HomeDataClassItem>?>, t: Throwable) {
                ShowPostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("Hellopost", "onActivityResult: failed" )
            }
        })
        return ShowPostLiveData
    }
}


