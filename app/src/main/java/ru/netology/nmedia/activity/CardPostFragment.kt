package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.PostArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class CardPostFragment : Fragment() {
    companion object {
        var Bundle.postArg: Post? by PostArg
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCardPostBinding.inflate(inflater, container, false)
        arguments?.postArg?.let {
            val cardPost = it
            viewModel.data.observe(viewLifecycleOwner) { post ->
                with(binding.postContent) {
                    post.map { post ->
                        author.text = cardPost.author
                        published.text = cardPost.published
                        content.text = cardPost.content
                        like.isChecked = it.likedByMe
                        like.text = viewModel.counting(cardPost.likes)
                        shared.text = viewModel.counting(cardPost.shares)
                        if (!it.videoUrl.isNullOrEmpty()) {
                            video.visibility = View.VISIBLE
                        } else video.visibility = View.GONE
                        menu.setOnClickListener {
                            PopupMenu(it.context, it).apply {
                                inflate(R.menu.options_post)
                                setOnMenuItemClickListener { item ->
                                    when (item.itemId) {
                                        R.id.remove -> {
                                            viewModel.removeById(cardPost.id)
                                            findNavController().navigateUp()
                                            true
                                        }
                                        R.id.edit -> {
                                            findNavController().navigate(R.id.editPostFragment,
                                                Bundle().apply
                                                {
                                                    textArg = cardPost.content
                                                    viewModel.edit(post)
                                                })
                                            true
                                        }
                                        else -> false
                                    }
                                }
                            }.show()
                        }
                        like.setOnClickListener {
                            viewModel.likeById(cardPost.id)
                        }
                        shared.setOnClickListener {
                            viewModel.shareById(cardPost.id)
                            Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, post.content)
                                type = "text/plane"
                            }
                        }
                        video.setOnClickListener {
                            val intentVideo =
                                Intent(Intent.ACTION_VIEW, Uri.parse(cardPost.videoUrl))
                            startActivity(intentVideo)

                        }
                    }
                }
            }
        }
        return binding.root
    }
}
