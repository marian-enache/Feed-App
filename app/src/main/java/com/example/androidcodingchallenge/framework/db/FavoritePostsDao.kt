package com.example.androidcodingchallenge.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface FavoritePostsDao {

  @Insert(onConflict = REPLACE)
  suspend fun addFavoritePost(favoritePost: FavoritePostEntity): Long

  @Query("SELECT * FROM favorite_posts WHERE postId = :postId LIMIT 1")
  suspend fun getFavoritePost(postId: String): FavoritePostEntity

  @Query("SELECT * FROM favorite_posts")
  suspend fun getAllFavoritePosts(): List<FavoritePostEntity>

  @Delete
  suspend fun removeFavoritePost(favoritePost: FavoritePostEntity): Int
}