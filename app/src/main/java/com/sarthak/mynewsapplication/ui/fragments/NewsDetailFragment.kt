package com.sarthak.mynewsapplication.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sarthak.mynewsapplication.R
import com.sarthak.mynewsapplication.databinding.FragmentNewsDetailBinding
import com.sarthak.mynewsapplication.ui.viewmodels.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsDetailFragment : Fragment() {
    private val viewModel: NewsDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            viewModel.newsItem.flowWithLifecycle(lifecycle)
                .collectLatest { newsItem ->
                    binding.newsItem = newsItem
                    if (newsItem.isBookmarked) {
                        binding.bookmarkButton.setImageResource(R.drawable.baseline_bookmark_24)
                    } else {
                        binding.bookmarkButton.setImageResource(R.drawable.baseline_bookmark_border_24)
                    }

                    if(newsItem.url.isEmpty()) {
                        binding.openUrlButton.visibility = GONE
                    } else {
                        binding.openUrlButton.visibility = VISIBLE
                    }
                }
        }

        binding.setBookmarkClickListener {
            lifecycleScope.launch {
                viewModel.toggleBookmark()
            }
        }

        binding.setOpenUrlClickListener {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.newsItem!!.url))
            startActivity(myIntent)
        }

        binding.setGoBackClickListener {
            it.findNavController().navigateUp()
        }
        return binding.root
    }
}