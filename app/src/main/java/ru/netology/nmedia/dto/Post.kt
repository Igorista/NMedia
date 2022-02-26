package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Long,
    var shares: Long,
    var views: Long,
    val likedByMe: Boolean,
    val shared: Boolean,
    val videoUrl: String?
): Parcelable



