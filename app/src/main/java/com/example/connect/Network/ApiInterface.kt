package com.example.connect.Network

import com.example.connect.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("/api/signup/sendotp/")
    fun signup(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("/api/login/")
    fun login(@Body data: AuthDataClass): Call<AuthDataClass>
    @POST("/api/signup/verify/")
    fun otp(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("/api/signup/")
    fun createPassword(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("/api/reset/")
    fun Verify(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("/api/reset/verify/")
    fun EnterOtp(@Body data: AuthDataClass): Call<ResponseBody>
    @PATCH("/api/changepsw/")
    fun ForgetPassword(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("api/createusername/")
    fun createUsername(@Body data: AuthDataClass):Call<AuthDataClass>
    @POST("/api/user/post/create/")
    fun uploadpost(@Body data: PostDataClass):Call<ResponseBody>
    @GET("api/user/post/")
    fun showPost():Call<List<HomeDataClassItem>>
    @POST("api/user/profile/")
    fun viewProfile(@Body data: Profile):Call<Profile>
    @POST("api/user/profile/post/")
    fun viewOtherPost(@Body data: Profile):Call<List<OthersPost>>
    @POST("api/user/search/profile/")
    fun searchProfile(@Body data: SearchProfileDataClassItem):Call<List<SearchProfileDataClassItem>>
    @POST("api/user/search/tag/")
    fun searchTag(@Body data: SearchTagDataClass):Call<List<SearchTagDataClass>>
}