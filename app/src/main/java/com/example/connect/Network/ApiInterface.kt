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
    fun createPassword(@Body data: AuthDataClass): Call<AuthDataClass>
    @POST("/api/reset/")
    fun Verify(@Body data: AuthDataClass): Call<ResponseBody>
    @POST("/api/reset/verify/")
    fun EnterOtp(@Body data: AuthDataClass): Call<ResponseBody>
    @PATCH("/api/changepsw/")
    fun ForgetPassword(@Body data: AuthDataClass): Call<AuthDataClass>
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
    @POST("api/user/tag/")
    fun seeTag(@Body data:OthersPost):Call<List<OthersPost>>
    @PUT("api/user/profile/follow/")
    fun sendRequest(@Body data:Profile):Call<EditProfileDataClass>
    @GET("api/user/follow/")
    fun showRequest():Call<List<ShowFollowRequestDataClass>>
    @POST("api/user/follow/")
    fun acceptRequest(@Body data:ShowFollowRequestDataClass):Call<ResponseBody>
    @PATCH("api/user/profile/")
    fun editProfile(@Body data:EditProfileDataClass):Call<ResponseBody>
    @GET("api/user/home/story/")
    fun homeStory():Call<List<HomeStoryDataClass>>
    @POST("api/user/story/")
    fun createStory(@Body data:CreateStoryDataClass):Call<CreateStoryDataClass>
    @POST("api/user/story/get/")
    fun showStory(@Body data:ShowStoryDataClass):Call<List<ShowStoryDataClass>>
//    @DELETE("api/user/story/")
//    fun deleteStory(@Body data:ShowStoryDataClass):Call<ResponseBody>
    @PUT("api/user/post/like/")
    fun likePost(@Body data:LikePostDataClass):Call<LikePostDataClass>
    @PUT("api/user/post/comment/")
    fun createComment(@Body data:CommentDataClass):Call<CommentDataClass>
    @POST("api/user/post/comment/")
    fun showComment(@Body data:CommentDataClass):Call<List<CommentDataClass>>
    @POST("api/user/profile/bookmark/")
    fun createBookmark(@Body data:HomeDataClassItem):Call<HomeDataClassItem>
}