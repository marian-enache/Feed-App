package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.mappers.CommentModelDataMapper
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject

interface GetTopCommentsAndPositions {
    suspend fun call(positions: List<Int>): List<CommentModel>
}

class GetTopCommentsAndPositionsImpl @Inject constructor(
    private val repository: FeedItemsRepository,
    private val mapper: CommentModelDataMapper
) : GetTopCommentsAndPositions {

    override suspend fun call(positions: List<Int>): List<CommentModel> {
        val comments = mapper.transform(
            repository.getTopComments()
                .sortedBy { comment -> comment.postId }
                .take(positions.size)
        )

        val topComments = mutableListOf<CommentModel>()

        comments.forEach { comment ->
            comment.position = positions[topComments.size]
            topComments.add(comment)
        }

        return topComments
    }
}