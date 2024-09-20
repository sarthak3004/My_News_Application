package com.sarthak.mynewsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sarthak.mynewsapplication.adapters.NewsAdapter
import com.sarthak.mynewsapplication.databinding.FragmentNewsBinding
import com.sarthak.mynewsapplication.ui.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = NewsAdapter(
            onBookmarkClick = { newsItem ->
                lifecycleScope.launch {
                    viewModel.toggleBookmark(newsItem)
                }
            }
        )
        binding.newsItemList.adapter = adapter
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle)
                .collectLatest { state ->
                    when {
                        state.isLoading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.errorTextView.visibility = View.GONE
                            binding.newsItemList.visibility = View.GONE
                        }
                        state.isError -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorTextView.visibility = View.VISIBLE
                            binding.newsItemList.visibility = View.GONE
                            binding.errorTextView.text = state.errorMessage
                        }
                        else -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorTextView.visibility = View.GONE
                            binding.newsItemList.visibility = View.VISIBLE
                            adapter.submitList(state.newsResponse.newsItems)
                        }
                    }
                    adapter.submitList(state.newsResponse.newsItems)
                }
        }
        return binding.root
    }
}