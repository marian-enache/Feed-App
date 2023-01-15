package com.example.androidcodingchallenge.data.models

data class CommentModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val body: String,
    override var position: Int = -1
): FeedItemModel