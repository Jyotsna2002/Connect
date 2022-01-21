package com.example.connect.model

data class ShowFollowRequestDataClass(
    val from_user: Int?=null,
    val from_user_profile_photo: String?=null,
    val from_user_username: String?=null,
    val id: Int?=null,
    val to_user: Int?=null,
    val to_user_profile_photo: String?=null,
    val to_user_username: String?=null,
    val follow_id: Int?=null,
    val confirm:String?=null
)