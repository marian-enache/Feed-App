package com.example.androidcodingchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.androidcodingchallenge.BuildConfig
import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import com.example.androidcodingchallenge.data.usecases.*
import com.example.androidcodingchallenge.framework.RoomFavoritePostsDataSource
import com.example.androidcodingchallenge.framework.db.AppDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJodelApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGetPosts(useCase: GetPostsImpl): GetPosts = useCase

    @Provides
    @Singleton
    fun provideGetTopCommentsAndPositions(useCase: GetTopCommentsAndPositionsImpl): GetTopCommentsAndPositions = useCase

    @Provides
    @Singleton
    fun provideGetPhotosPositions(useCase: GetPhotosPositionsImpl): GetPhotosPositions = useCase

    @Provides
    @Singleton
    fun provideGetPostComments(useCase: GetPostCommentsImpl): GetPostComments = useCase

    @Provides
    @Singleton
    fun provideAddPostToFavorites(useCase: AddPostToFavoritesImpl): AddPostToFavorites = useCase

    @Provides
    @Singleton
    fun provideRemovePostFromFavorites(useCase: RemovePostFromFavoritesImpl): RemovePostFromFavorites = useCase

    @Provides
    @Singleton
    fun provideGetFavoritePosts(useCase: GetFavoritePostsImpl): GetFavoritePosts = useCase

    @Provides
    @Singleton
    fun provideFavoritePostsDataSource(roomFavoritePostsDataSource: RoomFavoritePostsDataSource): FavoritePostsDataSource = roomFavoritePostsDataSource

    @Provides
    @Singleton
    internal fun providesDispatchersProvider(dispatchersProvider: DispatchersProviderImpl): DispatchersProvider = dispatchersProvider
}