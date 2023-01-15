package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.mappers.CommentModelDataMapper
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject

interface GetTopCommentsAndPositions {
    suspend fun call(): List<CommentModel>
}

class GetTopCommentsAndPositionsImpl @Inject constructor(
    private val repository: FeedItemsRepository,
    private val mapper: CommentModelDataMapper
) : GetTopCommentsAndPositions {

    override suspend fun call(): List<CommentModel> {
        val comments = mapper.transform(
            repository.getTopComments()
                .sortedBy { comment -> comment.postId }
                .take(COMMENTS_COUNT)
        )

        val topComments = mutableListOf<CommentModel>()

        comments.forEach { comment ->
            comment.position = commentsPositions[topComments.size]
            topComments.add(comment)
        }

        return topComments
    }

    companion object {
        private const val COMMENTS_COUNT = 5
        private val commentsPositions = listOf(3, 7, 15, 23, 40)
    }
}