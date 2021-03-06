package com.example.connect.model

data class HomeDataClassItem(
    val caption: String?=null,
    val liked_by: List<String>?=null,
    val post_id: Int?=null,
    val post_image: List<PostImage>?=null,
    val post_video: List<PostVideo>?=null,
    val user: Int?=null,
    val no_of_likes: Int?=null,
    val user_name:String?=null,
    val Like:Boolean?=null,
    val Bookmark:Boolean?=null,
    val bookmarked:Boolean?=null,
    val profile_picture:String?=null
)