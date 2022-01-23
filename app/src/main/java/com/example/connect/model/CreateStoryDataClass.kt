package com.example.connect.model

data class CreateStoryDataClass(
    val photos: List<String>?=null,
    val videos: List<String>?=null,
    val id: Int?=null,
    val is_seen: Boolean?=null,
    val user: Int?=null
)