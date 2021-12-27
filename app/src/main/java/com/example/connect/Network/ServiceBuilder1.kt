package com.example.connect.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder1 {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://instagram-si.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}