package com.example.androidcodingchallenge.ui.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallenge.R
import com.example.androidcodingchallenge.databinding.ItemCommentBinding
import com.example.androidcodingchallenge.databinding.ItemPhotoBinding
import com.example.androidcodingchallenge.databinding.ItemPostBinding
import com.example.androidcodingchallenge.domain.models.Comment
import com.example.androidcodingchallenge.domain.models.FeedItem
import com.example.androidcodingchallenge.domain.models.Photo
import com.example.androidcodingchallenge.domain.models.Post
import com.squareup.picasso.Picasso

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feedItems = emptyList<FeedItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFeed(feedItems: List<FeedItem>) {
        this.feedItems = feedItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == FEED_TYPE_POST) {
            val binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            PostViewHolder(binding)
        } else if (viewType == FEED_TYPE_COMMENT) {
            val binding = ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            CommentViewHolder(binding)
        } else {
            val binding = ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            PhotoViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedItems[position])
    }

    override fun getItemCount(): Int = feedItems.size

    override fun getItemViewType(position: Int): Int {
        return when (feedItems[position]) {
            is Post -> FEED_TYPE_POST
            is Comment -> FEED_TYPE_COMMENT
            else -> FEED_TYPE_PHOTO
        }
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(feedItem: FeedItem)
    }

    class PostViewHolder(private val binding: ItemPostBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItem) {
            if (feedItem is Post) {
                binding.tvTitle.text = feedItem.title
                binding.tvBody.text = feedItem.body
            }
        }
    }

    class CommentViewHolder(private val binding: ItemCommentBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItem) {
            if (feedItem is Comment) {
                binding.tvBody.text = feedItem.body
            }
        }
    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItem) {
            if (feedItem is Photo) {
                binding.tvTitle.text = feedItem.title
                Picasso.get()
                    .load(feedItem.url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.ivPhoto)
            }
        }
    }

    companion object {
        private const val FEED_TYPE_POST = 0
        private const val FEED_TYPE_COMMENT = 1
        private const val FEED_TYPE_PHOTO = 2
    }

}