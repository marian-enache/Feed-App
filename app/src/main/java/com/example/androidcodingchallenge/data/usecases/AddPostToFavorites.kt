package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import com.example.androidcodingchallenge.data.models.PostModel
import javax.inject.Inject

interface AddPostToFavorites {
    suspend fun call(post: PostModel): Boolean
}

class AddPostToFavoritesImpl @Inject constructor(private val dataSource: FavoritePostsDataSource) :
    AddPostToFavorites {

    override suspend fun call(post: PostModel) =
        dataSource.add(post) > 0
}