package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import com.example.androidcodingchallenge.data.models.PostModel
import javax.inject.Inject

interface RemovePostFromFavorites {
    suspend fun call(post: PostModel): Boolean
}

class RemovePostFromFavoritesImpl @Inject constructor(private val dataSource: FavoritePostsDataSource) :
    RemovePostFromFavorites {

    override suspend fun call(post: PostModel) =
        dataSource.remove(post) > 0
}