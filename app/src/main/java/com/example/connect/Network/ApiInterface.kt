package com.example.connect.Network

import com.example.connect.Repository.AuthDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiInterface {
    @POST("api/user/signup/sendotp/")
    fun signup(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("api/user/login/")
    fun login(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("api/user/signup/verify/")
    fun otp(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("api/user/signup/")
    fun createPassword(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("api/user/reset/")
    fun Verify(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("api/user/reset/verify/")
    fun EnterOtp(@Body data: AuthDataClass): Call<ResponseBody>
    @PATCH("api/user/changepsw/")
    fun ForgetPassword(@Body data: AuthDataClass): Call<ResponseBody>
}