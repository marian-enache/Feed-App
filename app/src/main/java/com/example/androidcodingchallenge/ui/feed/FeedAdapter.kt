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
import com.example.androidcodingchallenge.databinding.ItemPhotoBinding
import com.example.androidcodingchallenge.databinding.ItemPostBinding
import com.squareup.picasso.Picasso

class FeedAdapter(private val postCallback: PostViewHolder.Callback) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feedItems = emptyList<FeedItemModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFeed(feedItems: List<FeedItemModel>) {
        this.feedItems = feedItems
        notifyDataSetChanged()
    }

    fun feedItemChanged(position: Int) {
        notifyItemChanged(position)
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
                // todo make it uncheckable
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

    companion object {
        private const val FEED_TYPE_POST = 0
        private const val FEED_TYPE_COMMENT = 1
        private const val FEED_TYPE_PHOTO = 2
    }
}