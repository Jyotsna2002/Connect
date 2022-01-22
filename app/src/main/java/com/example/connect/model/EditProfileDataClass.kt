package com.example.connect.model

data class EditProfileDataClass(
    val bio: String?=null,
    val is_private: Boolean?=null,
    val profile_photo: String?=null,
    val username: String?=null,
    val follow:Boolean?=null
)