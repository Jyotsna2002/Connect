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

       // var post = ArrayList<HomeDataClassItem>()

    private val ShowPostLiveData= MutableLiveData<Response<List<HomeDataClassItem>>>()

//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: HomePageAdapter
    fun ShowPost(): MutableLiveData<Response<List<HomeDataClassItem>>> {
        val call=service.showPost()
        ShowPostLiveData.postValue(Response.Loading())
                call.enqueue(object : Callback<List<HomeDataClassItem>?> {
                    override fun onResponse(
                        call: Call<List<HomeDataClassItem>?>,
                        response: retrofit2.Response<List<HomeDataClassItem>?>
                    ) {
                if (response.isSuccessful) {
                 //   Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show()
                    val responseBody = response.body()!!
                    ShowPostLiveData.postValue(Response.Success(responseBody))

          //          adapter.setUpdatedData(post)
//                    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                    adapter =HomePageAdapter()
//                    recyclerView.adapter = adapter
//                    adapter = HomePageAdapter(context, post)
//                    recyclerView.adapter = adapter
//                    recyclerView.layoutManager =
//                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    // Toast.makeText(context,"Post Successfully Created",Toast.LENGTH_SHORT).show()
                    Log.i("Hellopost", "onActivityResult:"+responseBody)
                  //  Log.i("Hello", "onActivityResult:"+ post.indexOfFirst { true })
                }
                else {
                   ShowPostLiveData.postValue(Response.Error(response.message()))

          //          Toast.makeText(context,response.code(),Toast.LENGTH_SHORT).show()
                    Log.i("Hellopost", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<HomeDataClassItem>?>, t: Throwable) {
                ShowPostLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
          //      Toast.makeText(context,"Something went wrong ${t.message}",Toast.LENGTH_SHORT).show()
                Log.i("Hellopost", "onActivityResult: failed" )
            }
        })
        return ShowPostLiveData
    }
}


