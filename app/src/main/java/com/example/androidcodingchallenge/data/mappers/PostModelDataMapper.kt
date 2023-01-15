package com.example.androidcodingchallenge.data.mappers

import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.domain.models.Post
import javax.inject.Inject

class PostModelDataMapper @Inject constructor() {
    fun transform(post: Post): PostModel =
        PostModel(
            post.postId,
            post.title,
            post.body
        )

    fun transform(posts: List<Post>): List<PostModel> =
        posts.map {
            transform(it)
        }
}