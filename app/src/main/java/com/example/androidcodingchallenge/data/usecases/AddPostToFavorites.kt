package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import javax.inject.Inject


interface AddPostToFavorites {
    suspend fun call(postId: Int): Boolean
}

class AddPostToFavoritesImpl @Inject constructor(private val dataSource: FavoritePostsDataSource) :
    AddPostToFavorites {

    override suspend fun call(postId: Int) =
        dataSource.add(postId) > 0
}