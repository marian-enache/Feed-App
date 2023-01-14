package com.example.androidcodingchallenge.ui.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallenge.databinding.ItemCommentBinding
import com.example.androidcodingchallenge.databinding.ItemPostBinding
import com.example.androidcodingchallenge.domain.models.Comment
import com.example.androidcodingchallenge.domain.models.FeedItem
import com.example.androidcodingchallenge.domain.models.Post

// todo check if only one adapter could be used for both screens
class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postItems = emptyList<FeedItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPostItems(postItems: List<FeedItem>) {
        this.postItems = postItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == TYPE_POST) {
            val binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            PostViewHolder(binding)
        } else {
            val binding = ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            CommentViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postItems[position])
    }

    override fun getItemCount(): Int = postItems.size

    override fun getItemViewType(position: Int): Int {
        return when (postItems[position]) {
            is Post -> TYPE_POST
            else -> TYPE_COMMENT
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
                binding.tvTopComment.text = feedItem.name
                binding.tvBody.text = feedItem.body
            }
        }
    }

    companion object {
        private const val TYPE_POST = 0
        private const val TYPE_COMMENT = 1
    }
}