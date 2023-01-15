package com.example.androidcodingchallenge.domain.models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val postId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)