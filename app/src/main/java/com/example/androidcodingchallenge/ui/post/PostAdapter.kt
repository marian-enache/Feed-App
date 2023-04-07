package com.example.androidcodingchallenge.ui.post

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.ui.compose.CommentComposable
import com.example.androidcodingchallenge.ui.compose.PostComposable

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postItems = emptyList<FeedItemModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPostItems(postItems: List<FeedItemModel>) {
        this.postItems = postItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == TYPE_POST) {
            PostViewHolder(ComposeView(parent.context).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            })
        } else {
            CommentViewHolder(ComposeView(parent.context).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            })
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postItems[position])
    }

    override fun getItemCount(): Int = postItems.size

    override fun getItemViewType(position: Int): Int {
        return when (postItems[position]) {
            is PostModel -> TYPE_POST
            else -> TYPE_COMMENT
        }
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(feedItem: FeedItemModel)
    }

    class PostViewHolder(val composeView: ComposeView) : ViewHolder(composeView) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is PostModel) {
                composeView.setContent {
                    MaterialTheme {
                        PostComposable(feedItem, false)
                    }
                }
            }
        }
    }

    class CommentViewHolder(val composeView: ComposeView) : ViewHolder(composeView) {
        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is CommentModel) {
                composeView.setContent {
                    MaterialTheme {
                        CommentComposable(feedItem)
                    }
                }
            }
        }
    }

    companion object {
        private const val TYPE_POST = 0
        private const val TYPE_COMMENT = 1
    }
}