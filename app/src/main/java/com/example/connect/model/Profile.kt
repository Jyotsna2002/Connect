package com.example.connect.model

data class Profile(
    val active_story: Int?=null,
    val bio: String?=null,
    val no_of_followers: Int?=null,
    val no_of_following: Int?=null,
    val id: Int?=null,
    val is_follow: Boolean?=null,
    val is_private: Boolean?=null,
    val profile_photo: String?=null,
    val user: Int?=null,
    val user_name: String?=null,
    val username: String?=null,
    val user_id:Int?=null,
    val follow:Boolean?=null
)