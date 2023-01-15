package com.example.androidcodingchallenge.data.mappers

import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.domain.models.Photo
import javax.inject.Inject

class PhotoModelDataMapper @Inject constructor() {
    fun transform(photo: Photo): PhotoModel =
        PhotoModel(
            photo.id,
            photo.title,
            photo.url
        )

    fun transform(photos: List<Photo>): List<PhotoModel> =
        photos.map {
            transform(it)
        }
}