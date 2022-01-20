package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.SearchProfileDataClassItem
import retrofit2.Call
import retrofit2.Callback

class SearchProfileRepo(private val service: ApiInterface) {
    private val SearchProfileLiveData= MutableLiveData<Response<List<SearchProfileDataClassItem>>>()
    fun SearchProfile(s:String?): MutableLiveData<Response<List<SearchProfileDataClassItem>>> {
        Log.i("user_id", "Respone $s")
        val call=service.searchProfile(
            SearchProfileDataClassItem(
                search = s
            )
        )
        SearchProfileLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<SearchProfileDataClassItem>?> {
            override fun onResponse(
                call: Call<List<SearchProfileDataClassItem>?>,
                response: retrofit2.Response<List<SearchProfileDataClassItem>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    SearchProfileLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchProfile", "onActivityResult:$responseBody")
                }
                else {
                    SearchProfileLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchProfile", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<SearchProfileDataClassItem>?>, t: Throwable) {
                SearchProfileLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchProfile", "onActivityResult: failed" )
            }
        })
        return SearchProfileLiveData
    }
}