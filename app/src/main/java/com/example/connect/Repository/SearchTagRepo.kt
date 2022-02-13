package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.SearchTagDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SearchTagRepo{
    private val SearchTagLiveData= MutableLiveData<Response<List<SearchTagDataClass>>>()
   suspend fun SearchTag(search:String?,context:Context): MutableLiveData<Response<List<SearchTagDataClass>>> {
        Log.i("user_id", "Respone $search")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).searchTag(
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
                }  else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        SearchTag(search,context)
                    }
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