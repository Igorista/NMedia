package ru.netology.nmedia.dto.ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int,
    var shares: Int,
    var views : Int,
    val likedByMe: Boolean,
    val shared: Boolean,

)

