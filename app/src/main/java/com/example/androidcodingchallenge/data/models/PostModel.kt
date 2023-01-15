package com.example.androidcodingchallenge.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PostModel(
    val postId: Int,
    val title: String,
    val body: String,
    override var position: Int = -1,
    var isFavorite: Boolean = false
): FeedItemModel, Parcelable