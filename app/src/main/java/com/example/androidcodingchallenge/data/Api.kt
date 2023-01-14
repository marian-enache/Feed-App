package com.example.androidcodingchallenge.data

import com.example.androidcodingchallenge.domain.models.Comment
import com.example.androidcodingchallenge.domain.models.Photo
import com.example.androidcodingchallenge.domain.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/comments")
    suspend fun getComments(): Response<List<Comment>>

    @GET("/posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId") postId: Int): Response<List<Comment>>

    @GET("/photos")
    suspend fun getPhotos(): Response<List<Photo>>
}