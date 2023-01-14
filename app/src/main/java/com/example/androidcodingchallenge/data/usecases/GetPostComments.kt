package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.domain.models.Comment
import javax.inject.Inject

interface GetTopCommentsAndPositions {
    suspend fun call(): List<Comment>
}

class GetTopCommentsAndPositionsImpl @Inject constructor(private val api: Api) :
    GetTopCommentsAndPositions {
    override suspend fun call(): List<Comment> {
        val comments = api.getComments()
        val topComments = mutableListOf<Comment>()
        if (comments.isSuccessful) {
            run loop@{
                comments.body()?.sortedBy { it.postId }?.forEach {
                    if (topComments.isEmpty() || it.postId > topComments.last().postId) {
                        it.position = commentsPositions[topComments.size]
                        topComments.add(it)
                    }
                    if (topComments.size == 5) {
                        return@loop
                    }
                }
            }
        }
        return topComments
    }

    companion object {
        private val commentsPositions = listOf(3, 7, 15, 23, 40)
    }
}