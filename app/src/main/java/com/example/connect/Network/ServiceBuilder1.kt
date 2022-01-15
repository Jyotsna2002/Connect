package com.example.connect.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder1 {
    fun buildService(token: String?): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("http://65.1.114.5")
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
                chain.proceed(request)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)

    }
}