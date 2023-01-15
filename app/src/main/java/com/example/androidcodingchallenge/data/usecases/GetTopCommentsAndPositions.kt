package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject

interface GetTopCommentsAndPositions {
    suspend fun call(): List<CommentModel>
}

class GetTopCommentsAndPositionsImpl @Inject constructor(
    private val repository: FeedItemsRepository
) : GetTopCommentsAndPositions {

    override suspend fun call() =
        repository.getTopCommentsWithPositions(COMMENTS_COUNT, commentsPositions)

    companion object {
        private const val COMMENTS_COUNT = 5
        private val commentsPositions = listOf(3, 7, 15, 23, 40)
    }
}