package com.example.androidcodingchallenge.ui.post

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.ui.compose.CommentComposable
import com.example.androidcodingchallenge.ui.compose.EmptyListComposable
import com.example.androidcodingchallenge.ui.compose.PostComposable

@Composable
fun PostScreenComposable(viewModel: PostViewModel) {
    val commentsObs by viewModel.comments.observeAsState()
    commentsObs?.let { comments ->
        CommentsRecyclerView(comments)
    } ?: run {
        EmptyListComposable()
    }
}

@Composable
fun CommentsRecyclerView(
    feedItems: List<FeedItemModel>
) {
    if (feedItems.isEmpty()) return EmptyListComposable()

    LazyColumn(Modifier.fillMaxWidth()) {
        items(feedItems) { item ->
            when (item) {
                is PostModel -> PostComposable(item, false)
                is CommentModel -> CommentComposable(item)
            }
        }
    }
}