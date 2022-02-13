package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.SearchProfileDataClassItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SearchProfileRepo{
    private val SearchProfileLiveData= MutableLiveData<Response<List<SearchProfileDataClassItem>>>()
   suspend fun SearchProfile(s:String?,context:Context): MutableLiveData<Response<List<SearchProfileDataClassItem>>> {
        Log.i("user_id", "Respone $s")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).searchProfile(
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
                }  else if( response.code() == 406 ){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        SearchProfile(s,context)
                    }
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