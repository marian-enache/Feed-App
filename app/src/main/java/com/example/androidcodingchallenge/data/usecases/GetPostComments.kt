package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject

interface GetPostComments {
    suspend fun call(post: PostModel): List<CommentModel>
}

class GetPostCommentsImpl @Inject constructor(
    private val repository: FeedItemsRepository
) : GetPostComments {

    override suspend fun call(post: PostModel) = repository.getPostComments(post)
}