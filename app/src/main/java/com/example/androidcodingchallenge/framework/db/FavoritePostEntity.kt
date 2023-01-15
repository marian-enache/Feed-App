package com.example.androidcodingchallenge.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_posts")
data class FavoritePostEntity(
    @PrimaryKey val postId: Int
)