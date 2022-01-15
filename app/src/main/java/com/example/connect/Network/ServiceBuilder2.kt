package com.example.connect.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder2 {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://65.1.114.5")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}