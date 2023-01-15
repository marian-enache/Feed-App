package com.example.androidcodingchallenge.data.mappers

import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.domain.models.Comment
import javax.inject.Inject

class CommentModelDataMapper @Inject constructor() {
    fun transform(comment: Comment): CommentModel =
        CommentModel(
            comment.postId,
            comment.id,
            comment.name,
            comment.body
        )

    fun transform(comments: List<Comment>): List<CommentModel> =
        comments.map {
            transform(it)
        }
}