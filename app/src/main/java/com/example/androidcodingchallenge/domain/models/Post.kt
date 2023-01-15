package com.example.androidcodingchallenge.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val postId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    override var position: Int = -1,
    var isFavorite: Boolean = false
): FeedItem, Parcelable