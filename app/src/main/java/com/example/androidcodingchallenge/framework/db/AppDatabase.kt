package com.example.androidcodingchallenge.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritePostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "challenge.db"
    }

    abstract fun favoritePostsDao(): FavoritePostsDao
}