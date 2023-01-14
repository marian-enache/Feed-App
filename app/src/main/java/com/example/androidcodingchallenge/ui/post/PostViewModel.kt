package com.example.androidcodingchallenge.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingchallenge.data.usecases.GetPostComments
import com.example.androidcodingchallenge.di.DispatchersProvider
import com.example.androidcodingchallenge.domain.models.FeedItem
import com.example.androidcodingchallenge.domain.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostComments: GetPostComments,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _comments = MutableLiveData<List<FeedItem>>()
    val comments: LiveData<List<FeedItem>> get() = _comments

    fun onViewCreated(post: Post) {
        _comments.value = listOf(post)

        viewModelScope.launch(dispatchersProvider.io) {
            val postItems = mutableListOf<FeedItem>()

            val comments = getPostComments.call(post.postId)

            postItems.add(post)
            postItems.addAll(comments)
            _comments.postValue(postItems)
            // Chose to use only a recycler view instead of using a nested scrollView and a recycler view
            // as a multiple-scope Adapter was already implemented
        }
    }
}