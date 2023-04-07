package com.example.androidcodingchallenge.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingchallenge.data.Resource
import com.example.androidcodingchallenge.data.interactors.FeedItemsInteractor
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.di.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val interactor: FeedItemsInteractor,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _feedItems = MutableLiveData<Resource<List<FeedItemModel>>>()
    val feedItems: LiveData<Resource<List<FeedItemModel>>> get() = _feedItems

    fun onViewCreated() {
        _feedItems.postValue(Resource.loading())

        viewModelScope.launch(dispatchersProvider.io) {
            val feedItems = interactor.getFeedItems()

            withContext(dispatchersProvider.main) {
                _feedItems.postValue(Resource.success(feedItems))
            }
        }
    }

    fun onPostMarkedAsFavorite(post: PostModel, isMarked: Boolean) {
        viewModelScope.launch(dispatchersProvider.io) {
            if (isMarked) {
                val added = interactor.addPostToFavorites(post)

                if (added) {
                    withContext(dispatchersProvider.main) {
                        updateMarkedPost(post, true)
                    }
                }
            } else {
                val removed = interactor.removePostFromFavorites(post)

                if (removed) {
                    withContext(dispatchersProvider.main) {
                        updateMarkedPost(post, false)
                    }
                }
            }
        }
    }

    private fun updateMarkedPost(post: PostModel, marked: Boolean) {
        val items = _feedItems.value?.data ?: return
        val position = items.indexOfFirst {
            it is PostModel && it.postId == post.postId
        }

        (items[position] as PostModel).isFavorite = marked
    }
}