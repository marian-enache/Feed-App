package com.example.androidcodingchallenge.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.data.usecases.*
import com.example.androidcodingchallenge.di.DispatchersProvider
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

    private val _feedItems = MutableLiveData<List<FeedItemModel>>()
    val feedItems: LiveData<List<FeedItemModel>> get() = _feedItems

    private val _itemChanged = MutableLiveData<Int>()
    val itemChanged: LiveData<Int> get() = _itemChanged

    fun onViewCreated() {
        viewModelScope.launch(dispatchersProvider.io) {
            val feedItems = mutableListOf<FeedItemModel>()

            val posts = async {
                getPosts.call()
            }.await()

            val photos = async {
                getPhotosAndPositions.call()
            }.await()

            val comments = async {
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

            _feedItems.postValue(feedItems)
        }
    }

    fun onPostMarkedAsFavorite(post: PostModel, isMarked: Boolean) {
        viewModelScope.launch(dispatchersProvider.io) {
            if (isMarked) {
                val added = async {
                    getAddPostToFavorites.call(post)
                }.await()

                if (added) {
                    updateMarkedPost(post, true)
                }
            } else {
                val removed = async {
                    removePostFromFavorites.call(post)
                }.await()

                if (removed) {
                    updateMarkedPost(post, false)
                }
            }
        }
    }

    private fun updateMarkedPost(post: PostModel, marked: Boolean) {
        val position = _feedItems.value!!.indexOfFirst { it is PostModel && it.postId == post.postId }
        (_feedItems.value!![position] as? PostModel)?.isFavorite = marked

        _itemChanged.postValue(position)
    }
}