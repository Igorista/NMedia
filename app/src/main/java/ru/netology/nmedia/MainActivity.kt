package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import java.lang.Math.round


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            sheared = false,
            viewed = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_like_24)
            }

            root.setOnClickListener {
                Log.d("stuff", "stuff")
            }

            avatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            likesCount?.text = post.likes.toString()
            like?.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount?.text = counting(post.likes)
            }
            sheareCount?.text = post.shears.toString()
            sheared?.setOnClickListener {
                Log.d("stuff", "sheared")
                post.sheared = !post.sheared
                if (post.sheared) post.shears++
                sheareCount?.text = counting(post.shears)
            }
            viewsCount?.text = post.views.toString()
            viewed?.setOnClickListener {
                Log.d("stuff", "viewed")
                post.viewed = !post.viewed
                if (post.viewed) post.views++
                viewsCount?.text = counting(post.views)
            }
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