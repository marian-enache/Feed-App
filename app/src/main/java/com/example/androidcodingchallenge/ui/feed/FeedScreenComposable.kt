package com.example.androidcodingchallenge.ui.feed

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.androidcodingchallenge.data.Resource
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.ui.compose.*

@Composable
fun FeedScreenComposable(
    viewModel: FeedViewModel,
    onPostClicked: ((PostModel) -> Unit)? = null,
    onPostMarked: ((PostModel, Boolean) -> Unit)? = null
) {
    val feedItems by viewModel.feedItems.observeAsState()
    feedItems?.let { resource ->
        when (resource.status) {
            Resource.Status.LOADING -> ProgressBarComposable()
            Resource.Status.SUCCESS -> FeedItemsRecyclerView(resource.data!!, onPostClicked, onPostMarked)
            Resource.Status.ERROR -> EmptyListComposable()
        }
    } ?: run {
        EmptyListComposable()
    }
}

@Composable
fun FeedItemsRecyclerView(
    feedItems: List<FeedItemModel>,
    onPostClicked: ((PostModel) -> Unit)? = null,
    onPostMarked: ((PostModel, Boolean) -> Unit)? = null
) {
    if (feedItems.isEmpty()) return EmptyListComposable()

    LazyColumn(Modifier.fillMaxWidth()) {
        items(feedItems) { item ->
            when (item) {
                is PostModel -> PostComposable(item, true, onPostClicked, onPostMarked)
                is CommentModel -> CommentComposable(item)
                is PhotoModel -> ImageComposable(item)
            }
        }
    }
}