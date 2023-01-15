package com.example.androidcodingchallenge.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingchallenge.data.usecases.*
import com.example.androidcodingchallenge.di.DispatchersProvider
import com.example.androidcodingchallenge.domain.models.Comment
import com.example.androidcodingchallenge.domain.models.FeedItem
import com.example.androidcodingchallenge.domain.models.Photo
import com.example.androidcodingchallenge.domain.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getPosts: GetPosts,
    private val getTopCommentsAndPositions: GetTopCommentsAndPositions,
    private val getPhotosAndPositions: GetPhotosPositions,
    private val getAddPostToFavorites: AddPostToFavorites,
    private val removePostFromFavorites: RemovePostFromFavorites,
    private val getFavoritePosts: GetFavoritePosts,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _feedItems = MutableLiveData<List<FeedItem>>()
    val feedItems: LiveData<List<FeedItem>> get() = _feedItems

    private val _itemChanged = MutableLiveData<Int>()
    val itemChanged: LiveData<Int> get() = _itemChanged

    fun onViewCreated() {
        viewModelScope.launch(dispatchersProvider.io) {
            val feedItems = mutableListOf<FeedItem>()

            val posts: List<Post> = async {
                val response = getPosts.call()
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@async it
                    }
                }
                return@async emptyList()
            }.await()

            val photos: List<Photo> = async {
                getPhotosAndPositions.call()
            }.await()

            val comments: List<Comment> = async {
                getTopCommentsAndPositions.call()
            }.await()

            val favoritePosts = async {
                getFavoritePosts.call()
            }.await()

            favoritePosts.forEach { favId ->
                posts.first { it.postId == favId }.isFavorite = true
            }

            feedItems.addAll(posts)

            (comments + photos).sortedBy { it.position }.forEach {
                // prioritize top comments positions over photos positions,
                // so, if there is a comment at the same position a photo should be,
                // the photo position will be increased by 1

                var position: Int = -1

                if (it is Comment)
                    position = it.position
                if (it is Photo) {
                    position = it.position
                    while (feedItems[position] is Comment) {
                        position++
                    }
                }

                if (feedItems.size >= position) {
                    feedItems.add(position, it)
                }
            }

            _feedItems.postValue(feedItems)
        }
    }

    fun onPostMarkedAsFavorite(post: Post, isMarked: Boolean) {
        viewModelScope.launch(dispatchersProvider.io) {
            val postId = post.postId
            if (isMarked) {
                val added = async {
                    getAddPostToFavorites.call(postId)
                }.await()

                if (added) {
                    val position = _feedItems.value!!.indexOfFirst { it is Post && it.postId == postId }
                    (_feedItems.value!![position] as? Post)?.isFavorite = true

                    _itemChanged.postValue(position)
                }
            } else {
                val removed = async {
                    removePostFromFavorites.call(postId)
                }.await()

                if (removed) {
                    val position = _feedItems.value!!.indexOfFirst { it is Post && it.postId == postId }
                    (_feedItems.value!![position] as? Post)?.isFavorite = false

                    _itemChanged.postValue(position)
                }
            }
        }
    }
}