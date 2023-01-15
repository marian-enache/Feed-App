package com.example.androidcodingchallenge.data

interface FavoritePostsDataSource {
     // TODO: mapper added in impl
    suspend fun add(postId: Int): Long

    suspend fun readAll(): List<Int>

    suspend fun remove(postId: Int): Int
}