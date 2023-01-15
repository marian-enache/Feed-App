package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.mappers.PhotoModelDataMapper
import com.example.androidcodingchallenge.data.models.PhotoModel
import javax.inject.Inject


interface GetPhotosPositions {
    suspend fun call(): List<PhotoModel>
}

class GetPhotosPositionsImpl @Inject constructor(
    private val api: Api,
    private val mapper: PhotoModelDataMapper
) : GetPhotosPositions {

    override suspend fun call(): List<PhotoModel> {
        val response = api.getPhotos()
        val positionedPhotos = mutableListOf<PhotoModel>()

        if (response.isSuccessful) {
            response.body()?.let {
                val photos = mapper.transform(it.take(IMAGES_COUNT))
                photos.forEach { photo ->
                        photo.position = photosPositions[positionedPhotos.size]
                        positionedPhotos.add(photo)

                        if (positionedPhotos.size == IMAGES_COUNT) {
                            return@let
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