package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 998,
    var shears: Int = 999,
    var views : Int = 1000000,
    var likedByMe: Boolean = false,
    var sheared: Boolean = false,
    var viewed: Boolean = false,
)
