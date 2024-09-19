package com.sarthak.mynewsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sarthak.mynewsapplication.databinding.NewsItemBinding
import com.sarthak.mynewsapplication.domain.model.NewsItem

class NewsAdapter: ListAdapter<NewsItem, RecyclerView.ViewHolder>(NewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsItemViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsItem = getItem(position)
        (holder as NewsItemViewHolder).bind(newsItem)
    }

    class NewsItemViewHolder(
        private val binding: NewsItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsItem) {
            binding.apply {
                newsItem = item
                executePendingBindings()
            }
        }
    }

}


private class NewsDiffCallback : DiffUtil.ItemCallback<NewsItem>() {

    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem == newItem
    }
}