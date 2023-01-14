package com.example.androidcodingchallenge.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingchallenge.data.usecases.GetPhotosPositions
import com.example.androidcodingchallenge.data.usecases.GetPosts
import com.example.androidcodingchallenge.data.usecases.GetTopCommentsAndPositions
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
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _feedItems = MutableLiveData<List<FeedItem>>()
    val feedItems: LiveData<List<FeedItem>> get() = _feedItems

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
}