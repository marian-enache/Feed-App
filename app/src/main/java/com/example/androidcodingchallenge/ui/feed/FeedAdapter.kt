package com.example.androidcodingchallenge.ui.feed

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.ui.compose.*

class FeedAdapter(private val postCallback: PostViewHolder.Callback) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feedItems = emptyList<FeedItemModel>()
    private var state: State = State.LOADING

    @SuppressLint("NotifyDataSetChanged")
    fun setFeed(feedItems: List<FeedItemModel>) {
        this.feedItems = feedItems
        state = State.DISPLAY_ITEMS
        notifyDataSetChanged()
    }

    fun feedItemChanged(position: Int) {
        notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun startFullLoading() {
        state = State.LOADING
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == FEED_TYPE_POST) {
            PostViewHolder(ComposeView(parent.context).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            }, postCallback)
        } else if (viewType == FEED_TYPE_COMMENT) {
            CommentViewHolder(ComposeView(parent.context).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            })
        } else if (viewType == FEED_TYPE_PHOTO) {
            PhotoViewHolder(ComposeView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            })
        } else if (viewType == FEED_TYPE_EMPTY) {
            SimpleComposeViewHolder(ComposeView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                setContent { EmptyListComposable() }
            })
        } else {
            SimpleComposeViewHolder(ComposeView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                setContent { ProgressBarComposable() }
            })
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (feedItems.isNotEmpty()) holder.bind(feedItems[position])
    }

    override fun getItemCount(): Int =
        if (feedItems.isEmpty()) 1
        else feedItems.size

    override fun getItemViewType(position: Int): Int {
        if (state == State.LOADING) return FEED_TYPE_LOADING
        if (feedItems.isEmpty()) return FEED_TYPE_EMPTY

        return when (feedItems[position]) {
            is PostModel -> FEED_TYPE_POST
            is CommentModel -> FEED_TYPE_COMMENT
            else -> FEED_TYPE_PHOTO
        }
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(feedItem: FeedItemModel)
    }

    class PostViewHolder(
        private val composeView: ComposeView,
        private val callback: Callback
    ) : ViewHolder(composeView) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is PostModel) {
                composeView.setContent {
                    MaterialTheme {
                        PostComposable(feedItem,
                            onPostClicked = { callback.onPostClicked(it) },
                            onPostMarked = { postModel, marked -> callback.onPostMarked(postModel, marked) }
                        )
                    }
                }
            }
        }

        interface Callback {
            fun onPostClicked(post: PostModel)
            fun onPostMarked(post: PostModel, isMarked: Boolean)
        }
    }

    class CommentViewHolder(private val composeView: ComposeView) : ViewHolder(composeView) {
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

    class PhotoViewHolder(private val composeView: ComposeView) : ViewHolder(composeView) {
        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is PhotoModel) {
                composeView.setContent {
                    MaterialTheme {
                        ImageComposable(feedItem)
                    }
                }
            }
        }
    }

    class SimpleComposeViewHolder(composeView: ComposeView) : ViewHolder(composeView) {
        override fun bind(feedItem: FeedItemModel) {}
    }

    enum class State { LOADING, DISPLAY_ITEMS }

    companion object {
        private const val FEED_TYPE_POST = 0
        private const val FEED_TYPE_COMMENT = 1
        private const val FEED_TYPE_PHOTO = 2
        private const val FEED_TYPE_EMPTY = 3
        private const val FEED_TYPE_LOADING = 4
    }
}