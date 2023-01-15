package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.mappers.CommentModelDataMapper
import com.example.androidcodingchallenge.data.models.CommentModel
import javax.inject.Inject

interface GetPostComments {
    suspend fun call(postId: Int): List<CommentModel>
}

class GetPostCommentsImpl @Inject constructor(
    private val api: Api,
    private val mapper: CommentModelDataMapper
) : GetPostComments {
    override suspend fun call(postId: Int): List<CommentModel> {
        val response = api.getPostComments(postId)
        if (response.isSuccessful) {
            response.body()?.let {
                return mapper.transform(it)
            }
        }
        return emptyList()
    }
}