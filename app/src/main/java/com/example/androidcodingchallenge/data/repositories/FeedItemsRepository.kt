package com.example.androidcodingchallenge.data.repositories

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.NetworkHandler
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.domain.models.Comment
import com.example.androidcodingchallenge.domain.models.Photo
import com.example.androidcodingchallenge.domain.models.Post
import javax.inject.Inject

class FeedItemsRepository @Inject constructor(
    private val api: Api,
    private val networkHandler: NetworkHandler
) {

    suspend fun getPosts(): List<Post> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getPosts()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        return emptyList()
    }

    suspend fun getTopComments(): List<Comment> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getComments()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        return emptyList()
    }

    suspend fun getPhotos(): List<Photo> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getPhotos()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        return emptyList()
    }

    suspend fun getPostComments(post: PostModel): List<Comment> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getPostComments(post.postId)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        return emptyList()
    }
}