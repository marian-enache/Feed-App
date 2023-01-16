package com.example.androidcodingchallenge.ui.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.models.FeedItemModel
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.databinding.ItemCommentBinding
import com.example.androidcodingchallenge.databinding.ItemPostBinding

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postItems = emptyList<FeedItemModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPostItems(postItems: List<FeedItemModel>) {
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
            is PostModel -> TYPE_POST
            else -> TYPE_COMMENT
        }
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(feedItem: FeedItemModel)
    }

    class PostViewHolder(private val binding: ItemPostBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is PostModel) {
                binding.tvTitle.text = feedItem.title
                binding.tvBody.text = feedItem.body
                binding.btnMarkFavourite.visibility = View.INVISIBLE
            }
        }
    }

    class CommentViewHolder(private val binding: ItemCommentBinding) : ViewHolder(binding.root) {

        override fun bind(feedItem: FeedItemModel) {
            if (feedItem is CommentModel) {
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