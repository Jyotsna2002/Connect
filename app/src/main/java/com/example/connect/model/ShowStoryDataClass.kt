package com.example.connect.model

data class ShowStoryDataClass(
    val id: Int?=null,
    val is_seen: Boolean?=null,
    val post_image: List<PostImage>?=null,
    val post_video: List<PostVideo>?=null,
    val profile_picture: String?=null,
    val user: Int?=null,
    val user_id: Int?=null,
    val user_name: String?=null,
    val story_id: Int?=null
)