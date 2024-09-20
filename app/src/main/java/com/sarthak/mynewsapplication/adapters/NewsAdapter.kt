package com.sarthak.mynewsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sarthak.mynewsapplication.R
import com.sarthak.mynewsapplication.databinding.NewsItemBinding
import com.sarthak.mynewsapplication.domain.model.NewsItem

class NewsAdapter(
    private val onBookmarkClick: (NewsItem) -> Unit
): ListAdapter<NewsItem, RecyclerView.ViewHolder>(NewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsItemViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onBookmarkClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsItem = getItem(position)
        (holder as NewsItemViewHolder).bind(newsItem)
    }

    class NewsItemViewHolder(
        private val binding: NewsItemBinding,
        private val onBookmarkClick: (NewsItem) -> Unit,
    ): RecyclerView.ViewHolder(binding.root) {


        fun bind(item: NewsItem) {
            binding.apply {
                newsItem = item
                if(item.isBookmarked) {
                    iconButtonBookmark.setImageResource(R.drawable.baseline_bookmark_24)
                } else {
                    iconButtonBookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
                }
                executePendingBindings()
                setBookmarkClickListener {
                    onBookmarkClick(item)
                }
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