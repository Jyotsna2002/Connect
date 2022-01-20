package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import retrofit2.Call
import retrofit2.Callback

class SearchTagRepo(private val service: ApiInterface) {
    private val SearchTagLiveData= MutableLiveData<Response<List<SearchTagDataClass>>>()
    fun SearchTag(search:String?): MutableLiveData<Response<List<SearchTagDataClass>>> {
        Log.i("user_id", "Respone $search")
        val call=service.searchTag(
            SearchTagDataClass(
                search = search
            )
        )
        SearchTagLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<List<SearchTagDataClass>?> {
            override fun onResponse(
                call: Call<List<SearchTagDataClass>?>,
                response: retrofit2.Response<List<SearchTagDataClass>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    SearchTagLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }
                else {
                    SearchTagLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<List<SearchTagDataClass>?>, t: Throwable) {
                SearchTagLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return SearchTagLiveData
    }
}