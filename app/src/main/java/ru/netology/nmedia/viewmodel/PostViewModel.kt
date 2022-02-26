package ru.netology.nmedia.viewmodel

import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl
import android.app.Application

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    shared = false,
    published = "",
    likes = 0,
    shares = 0,
    views = 0,
    videoUrl = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)


    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }
    fun cancel() {
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun removeById(id: Long) = repository.removeById(id)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun video() = repository.video()
}