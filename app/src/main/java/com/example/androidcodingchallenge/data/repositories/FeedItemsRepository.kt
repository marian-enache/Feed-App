package com.example.androidcodingchallenge.data.repositories

import com.example.androidcodingchallenge.data.Api
import com.example.androidcodingchallenge.data.NetworkHandler
import com.example.androidcodingchallenge.data.mappers.CommentModelDataMapper
import com.example.androidcodingchallenge.data.mappers.PhotoModelDataMapper
import com.example.androidcodingchallenge.data.mappers.PostModelDataMapper
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.models.PostModel
import javax.inject.Inject

class FeedItemsRepository @Inject constructor(
    private val api: Api,
    private val networkHandler: NetworkHandler,
    private val postMapper: PostModelDataMapper,
    private val commentMapper: CommentModelDataMapper,
    private val photoMapper: PhotoModelDataMapper
) {

    suspend fun getPosts(): List<PostModel> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getPosts()
        if (response.isSuccessful) {
            response.body()?.let {
                return postMapper.transform(it)
            }
        }
        return emptyList()
    }

    suspend fun getTopCommentsWithPositions(
        commentsCount: Int,
        commentsPositions: List<Int>
    ): List<CommentModel> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getComments()
        val topComments = mutableListOf<CommentModel>()

        if (response.isSuccessful) {
            response.body()?.let {
                val comments = commentMapper.transform(it.sortedBy { comment -> comment.postId })
                comments.forEach { comment ->
                    if (topComments.isEmpty() || comment.postId > topComments.last().postId) {
                        comment.position = commentsPositions[topComments.size]
                        topComments.add(comment)
                    }

                    if (topComments.size == commentsCount) {
                        return@let
                    }
                }
            }
        }
        return topComments
    }

    suspend fun getPhotosWithPositions(
        photosCount: Int,
        photosPositions: List<Int>
    ): List<PhotoModel> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getPhotos()
        val positionedPhotos = mutableListOf<PhotoModel>()

        if (response.isSuccessful) {
            response.body()?.let {
                val photos = photoMapper.transform(it.take(photosCount))
                photos.forEach { photo ->
                    photo.position = photosPositions[positionedPhotos.size]
                    positionedPhotos.add(photo)

                    if (positionedPhotos.size == photosCount) {
                        return@let
                    }
                }
            }
        }
        return positionedPhotos
    }

    suspend fun getPostComments(post: PostModel): List<CommentModel> {
        // Won't do a complex error handling mechanism as it is out of scope at this moment
        if (!networkHandler.isNetworkAvailable()) return emptyList()

        val response = api.getPostComments(post.postId)
        if (response.isSuccessful) {
            response.body()?.let {
                return commentMapper.transform(it)
            }
        }
        return emptyList()
    }

}