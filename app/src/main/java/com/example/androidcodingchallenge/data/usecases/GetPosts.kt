package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.mappers.PostModelDataMapper
import com.example.androidcodingchallenge.data.models.PostModel
import javax.inject.Inject

interface GetPosts {

    suspend fun call(): List<PostModel>
}

class GetPostsImpl @Inject constructor(
    private val api: Api,
    private val mapper: PostModelDataMapper
) : GetPosts {

    override suspend fun call(): List<PostModel> {
        val response = api.getPosts()
        if (response.isSuccessful) {
            response.body()?.let {
                return mapper.transform(it)
            }
        }
        return emptyList()
    }
}