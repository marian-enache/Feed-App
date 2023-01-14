package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.domain.models.Comment
import javax.inject.Inject

interface GetPostComments {
    suspend fun call(postId: Int): List<Comment>
}

class GetPostCommentsImpl @Inject constructor(private val api: Api) :
    GetPostComments {
    override suspend fun call(postId: Int): List<Comment> {
        val response = api.getPostComments(postId)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        return emptyList()
    }
}