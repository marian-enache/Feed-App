package com.example.androidcodingchallenge.ui.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallenge.R
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.databinding.ItemCommentBinding
import com.example.androidcodingchallenge.databinding.ItemEmptyBinding
import com.example.androidcodingchallenge.databinding.ItemLoadingBinding
import com.example.androidcodingchallenge.databinding.ItemPhotoBinding
import com.example.androidcodingchallenge.databinding.ItemPostBinding
import com.squareup.picasso.Picasso

class FeedAdapter(private val postCallback: PostViewHolder.Callback) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feedItems = emptyList<FeedItemModel>()
    private lateinit var state: State

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
            val binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            PostViewHolder(binding, postCallback)
        } else if (viewType == FEED_TYPE_COMMENT) {
            val binding = ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            CommentViewHolder(binding)
        } else if (viewType == FEED_TYPE_PHOTO) {
            val binding = ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            PhotoViewHolder(binding)
        } else if (viewType == FEED_TYPE_EMPTY){
            val binding = ItemEmptyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            EmptyViewHolder(binding)
        } else {
            val binding = ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            LoadingViewHolder(binding)
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

    class PostViewHolder(private val binding: ItemPostBinding,
                         private val callback: Callback) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is PostModel) {
                binding.tvTitle.text = feedItem.title
                binding.tvBody.text = feedItem.body
                binding.btnMarkFavourite.isChecked = feedItem.isFavorite

                binding.root.setOnClickListener {
                    callback.onPostClicked(feedItem)
                }
                binding.btnMarkFavourite.setOnClickListener { view ->
                    callback.onPostMarked(feedItem, (view as CheckBox).isChecked)
                }
            }
        }

        interface Callback {
            fun onPostClicked(post: PostModel)
            fun onPostMarked(post: PostModel, isMarked: Boolean)
        }
    }

    class CommentViewHolder(private val binding: ItemCommentBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is CommentModel) {
                binding.tvBody.text = feedItem.body
            }
        }
    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is PhotoModel) {
                binding.tvTitle.text = feedItem.title
                Picasso.get()
                    .load(feedItem.url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.ivPhoto)
            }
        }
    }

    class EmptyViewHolder(binding: ItemEmptyBinding) : ViewHolder(binding.root) {
        override fun bind(feedItem: FeedItemModel) {
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : ViewHolder(binding.root) {
        override fun bind(feedItem: FeedItemModel) {
        }
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