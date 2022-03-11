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
        val postId = arguments?.postArg?.id ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            with(binding.postContent) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.isChecked = post.likedByMe
                like.text = viewModel.counting(post.likes)
                shared.text = viewModel.counting(post.shares)
                if (!post.videoUrl.isNullOrEmpty()) {
                    video.visibility = View.VISIBLE
                } else video.visibility = View.GONE
                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(post.id)
                                    findNavController().navigateUp()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    findNavController().navigate(R.id.editPostFragment,
                                        Bundle().apply
                                        {
                                            textArg = post.content
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
                    viewModel.likeById(post.id)
                }
                shared.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plane"
                    }
                    val repostIntent = Intent.createChooser(intent, getString(R.string.chooser_repost))
                    startActivity(repostIntent)
                    viewModel.shareById(post.id)
                }
                video.setOnClickListener {
                    val intentVideo =
                        Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                    startActivity(intentVideo)

                }
            }
        }
        return binding.root
    }
}
