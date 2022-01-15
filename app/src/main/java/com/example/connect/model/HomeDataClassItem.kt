package com.example.connect.model

data class HomeDataClassItem(
    val caption: String?=null,
    val liked_by: List<String>?=null,
    val post_id: Int?=null,
    val post_image: List<PostImage>,
    val post_video: List<PostVideo>,
    val user: Int?=null
)