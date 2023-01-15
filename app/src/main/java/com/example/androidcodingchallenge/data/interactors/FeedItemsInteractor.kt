package com.example.androidcodingchallenge.data.interactors

import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.data.usecases.*
import javax.inject.Inject

class FeedItemsInteractor @Inject constructor(
    private val getPosts: GetPosts,
    private val getTopCommentsAndPositions: GetTopCommentsAndPositions,
    private val getPhotosAndPositions: GetPhotosPositions,
    private val addPostToFavorites: AddPostToFavorites,
    private val removePostFromFavorites: RemovePostFromFavorites,
    private val getFavoritePosts: GetFavoritePosts,
) {

    suspend fun getFeedItems(): List<FeedItemModel> {
        val feedItems = mutableListOf<FeedItemModel>()

        val posts = getPosts.call()
        val photos = getPhotosAndPositions.call()
        val comments = getTopCommentsAndPositions.call()
        val favoritePosts = getFavoritePosts.call()

        markFavoritePosts(posts, favoritePosts)

        feedItems.addAll(posts)

        orderFeedItems(feedItems, comments, photos)

        return feedItems
    }

    suspend fun addPostToFavorites(post: PostModel) =
        addPostToFavorites.call(post)

    suspend fun removePostFromFavorites(post: PostModel) =
        removePostFromFavorites.call(post)

    private fun markFavoritePosts(
        posts: List<PostModel>,
        favoritePosts: List<Int>
    ) {
        favoritePosts.forEach { favId ->
            posts.first { it.postId == favId }.isFavorite = true
        }
    }

    private fun orderFeedItems(
        feedItems: MutableList<FeedItemModel>,
        comments: List<CommentModel>,
        photos: List<PhotoModel>
    ) {

        (comments + photos).sortedBy { it.position }.forEach {
            // prioritize top comments positions over photos positions,
            // so, if there is a comment at the same position a photo should be,
            // the photo position will be increased by 1

            var position: Int = -1

            if (it is CommentModel)
                position = it.position
            if (it is PhotoModel) {
                position = it.position
                while (feedItems[position] is CommentModel) {
                    position++
                }
            }

            if (feedItems.size >= position) {
                feedItems.add(position, it)
            }
        }
    }

}