package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.mappers.CommentModelDataMapper
import com.example.androidcodingchallenge.data.models.CommentModel
import javax.inject.Inject

interface GetTopCommentsAndPositions {
    suspend fun call(): List<CommentModel>
}

class GetTopCommentsAndPositionsImpl @Inject constructor(
    private val api: Api,
    private val mapper: CommentModelDataMapper
) : GetTopCommentsAndPositions {

    override suspend fun call(): List<CommentModel> {
        val response = api.getComments()
        val topComments = mutableListOf<CommentModel>()

        if (response.isSuccessful) {
            response.body()?.let {
                val comments = mapper.transform(it.sortedBy { comment -> comment.postId })
                comments.forEach { comment ->
                    if (topComments.isEmpty() || comment.postId > topComments.last().postId) {
                        comment.position = commentsPositions[topComments.size]
                        topComments.add(comment)
                    }

                    if (topComments.size == COMMENTS_COUNT) {
                        return@let
                    }
                }
            }
        }
        return topComments
    }

    companion object {
        private const val COMMENTS_COUNT = 5
        private val commentsPositions = listOf(3, 7, 15, 23, 40)
    }
}