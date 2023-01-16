package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.mappers.PhotoModelDataMapper
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import javax.inject.Inject

interface GetPhotosPositions {
    suspend fun call(positions: List<Int>): List<PhotoModel>
}

class GetPhotosPositionsImpl @Inject constructor(
    private val repository: FeedItemsRepository,
    private val photoMapper: PhotoModelDataMapper
) : GetPhotosPositions {

    override suspend fun call(positions: List<Int>): List<PhotoModel> {
        val photos = photoMapper.transform(
            repository.getPhotos().take(positions.size)
        )
        val positionedPhotos = mutableListOf<PhotoModel>()

        photos.forEach { photo ->
            photo.position = positions[positionedPhotos.size]
            positionedPhotos.add(photo)
        }
        return positionedPhotos
    }

}