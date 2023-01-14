package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.domain.models.Photo
import javax.inject.Inject


interface GetPhotosPositions {
    suspend fun call(): List<Photo>
}

class GetPhotosPositionsImpl @Inject constructor(private val api: Api) : GetPhotosPositions {

    override suspend fun call(): List<Photo> {
        val photos = api.getPhotos()
        val positionedPhotos = mutableListOf<Photo>()

        if (photos.isSuccessful) {
            run {
                photos.body()?.take(IMAGES_COUNT)?.forEach {
                    it.position = photosPositions[positionedPhotos.size]
                    positionedPhotos.add(it)

                    if (positionedPhotos.size == IMAGES_COUNT) {
                        return@run
                    }
                }
            }
        }
        return positionedPhotos
    }

    companion object {
        private const val IMAGES_COUNT = 5
        private val photosPositions = listOf(0, 5, 20, 21, 45)
    }
}