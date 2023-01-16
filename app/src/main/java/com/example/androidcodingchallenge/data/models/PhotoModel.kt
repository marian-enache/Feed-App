package com.example.androidcodingchallenge.data.models

data class PhotoModel(
    val id: Int,
    val title: String,
    val url: String,
    override var position: Int = -1
): FeedItemModel