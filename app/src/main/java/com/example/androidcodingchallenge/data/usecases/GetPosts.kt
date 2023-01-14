package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.domain.models.Post
import retrofit2.Response
import javax.inject.Inject

interface GetPosts {

    suspend fun call(): Response<List<Post>>
}

class GetPostsImpl @Inject constructor(private val api: Api) : GetPosts {

    override suspend fun call(): Response<List<Post>> {
        return api.getPosts()
    }
}