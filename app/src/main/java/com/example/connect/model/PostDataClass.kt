package com.example.connect.model

data class PostDataClass(
    val caption: String?=null,
    val photos: List<String>,
    val videos: List<String>?=null
)