package com.example.connect.model

data class Notificationpage(
    val is_seen: Boolean?=null,
    val post: Int?=null,
    val post_preview_image: String?=null,
    val profile_picture: String?=null,
    val sender_username: String?=null,
    val text: String?=null
)