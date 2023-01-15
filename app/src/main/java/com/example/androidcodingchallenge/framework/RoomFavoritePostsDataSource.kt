package com.example.androidcodingchallenge.framework

import android.content.Context
import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import com.example.androidcodingchallenge.framework.db.AppDatabase
import com.example.androidcodingchallenge.framework.db.FavoritePostEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RoomFavoritePostsDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    appDatabase: AppDatabase
) : FavoritePostsDataSource {

    private val favoritePostsDao = appDatabase.favoritePostsDao()

    override suspend fun add(postId: Int): Long {
        return favoritePostsDao.addFavoritePost(
            FavoritePostEntity(postId)
        )
    }

    override suspend fun readAll(): List<Int> {
        return favoritePostsDao.getAllFavoritePosts().map { it.postId }
    }

    override suspend fun remove(postId: Int): Int {
        return favoritePostsDao.removeFavoritePost(
            FavoritePostEntity(postId = postId)
        )
    }
}