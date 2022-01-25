package com.example.connect.model

data class CommentDataClass(
    val author: String?=null,
    val comment_by: Int?=null,
    val content: String?=null,
    val id: Int?=null,
    val is_parent: Boolean?=null,
    val parent: Int?=null,
    val post: Int?=null,
    val replies: List<Reply>?=null,
    val reply_count: Int?=null,
    val profile_picture: String?=null
)