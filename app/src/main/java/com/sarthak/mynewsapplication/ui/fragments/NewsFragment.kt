package com.sarthak.mynewsapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

const val TAG2 = "FragmentNews"
@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()

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
                            if(state.newsResponse.newsItems.isEmpty()) {
                                binding.noNewsTextView.visibility = View.VISIBLE
                            } else {
                                binding.newsItemList.visibility = View.VISIBLE
                                adapter.submitList(state.newsResponse.newsItems)
                            }
                        }
                    }
                }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG2, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG2, "onPause")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG2, "onStart")
        lifecycleScope.launch {
            viewModel.refresh()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG2, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG2, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG2, "onDetach")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG2, "onAttach")
    }
}