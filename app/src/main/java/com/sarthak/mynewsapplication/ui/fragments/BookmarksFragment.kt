package com.sarthak.mynewsapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sarthak.mynewsapplication.adapters.NewsAdapter
import com.sarthak.mynewsapplication.databinding.FragmentBookmarksBinding
import com.sarthak.mynewsapplication.ui.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val TAG = "FragmentBookmarks"
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

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        lifecycleScope.launch {
            viewModel.refresh()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }
}