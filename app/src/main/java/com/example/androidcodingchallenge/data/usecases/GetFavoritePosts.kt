package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import javax.inject.Inject

interface GetFavoritePosts {
    suspend fun call(): List<Int>
}

class GetFavoritePostsImpl @Inject constructor(private val dataSource: FavoritePostsDataSource) :
    GetFavoritePosts {

    override suspend fun call(): List<Int> {
        return dataSource.readAll()
    }
}