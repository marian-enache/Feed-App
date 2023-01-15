package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject

interface GetPosts {

    suspend fun call(): List<PostModel>
}

class GetPostsImpl @Inject constructor(
    private val repository: FeedItemsRepository
) : GetPosts {

    override suspend fun call() = repository.getPosts()
}