package com.sarthak.mynewsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sarthak.mynewsapplication.adapters.NewsAdapter
import com.sarthak.mynewsapplication.databinding.FragmentBookmarksBinding
import com.sarthak.mynewsapplication.ui.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BookmarksFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBookmarksBinding.inflate(inflater, container, false)
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
                    if(state.bookmarks.isEmpty()) {
                        binding.noNewsTextView.visibility = View.VISIBLE
                        binding.newsItemList.visibility = View.GONE
                    } else {
                        binding.noNewsTextView.visibility = View.GONE
                        binding.newsItemList.visibility = View.VISIBLE
                        adapter.submitList(state.bookmarks)
                    }
                }
        }
        return binding.root
    }
}