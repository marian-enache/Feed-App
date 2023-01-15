package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import javax.inject.Inject

interface RemovePostFromFavorites {
    suspend fun call(postId: Int): Boolean
}

class RemovePostFromFavoritesImpl @Inject constructor(private val dataSource: FavoritePostsDataSource) :
    RemovePostFromFavorites {

    override suspend fun call(postId: Int) =
        dataSource.remove(postId) > 0
}