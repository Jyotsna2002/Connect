package com.example.connect.Network

import com.example.connect.Repository.Datastore
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder2 {
private val scope = CoroutineScope(SupervisorJob() + CoroutineName("token"))
    val datastore = this.let { Datastore(null) }
   val Token = scope.launch {
       datastore.getUserDetails(Datastore.ACCESS_TOKEN_KEY)
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://instagram-si.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${Token}").build()
            chain.proceed(request)
        }.build())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}