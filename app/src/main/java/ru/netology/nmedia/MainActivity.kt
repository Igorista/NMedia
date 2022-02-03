package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount?.text = counting(post.likes)
                if (post.shared) post.shares++
                sheareCount?.text = counting(post.shares)
                if (post.viewed) post.views++
                viewsCount?.text = counting(post.views)
            }
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
        binding.shared.setOnClickListener {
            viewModel.share()
        }
        binding.viewed.setOnClickListener {
            viewModel.view()
        }
    }
}

fun counting(count: Int): String {
    val formatCount = when {
        count in 1000..9999 -> {
            String.format("%.1fK", count / 1000.0)
        }
        count in 10000..999999 -> {
            String.format("%dK", count / 1000)
        }
        count >= 1000000 -> {
            String.format("%.1fM", count / 1000000.0)
        }
        else -> {
            count.toString()
        }
    }
    return formatCount
}