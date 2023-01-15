package com.example.androidcodingchallenge.data

import com.example.androidcodingchallenge.data.models.PostModel

interface FavoritePostsDataSource {
     // TODO: mapper added in impl
    suspend fun add(post: PostModel): Long

    suspend fun readAll(): List<Int>

    suspend fun remove(post: PostModel): Int
}