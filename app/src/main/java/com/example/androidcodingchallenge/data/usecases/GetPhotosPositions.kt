package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject


interface GetPhotosPositions {
    suspend fun call(): List<PhotoModel>
}

class GetPhotosPositionsImpl @Inject constructor(
    private val repository: FeedItemsRepository
) : GetPhotosPositions {

    override suspend fun call() = repository.getPhotosWithPositions(IMAGES_COUNT, photosPositions)

    companion object {
        private const val IMAGES_COUNT = 5
        private val photosPositions = listOf(0, 5, 20, 21, 45)
    }
}