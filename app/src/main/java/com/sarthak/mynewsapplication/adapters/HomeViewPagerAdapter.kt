package com.sarthak.mynewsapplication.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sarthak.mynewsapplication.ui.fragments.BookmarksFragment
import com.sarthak.mynewsapplication.ui.fragments.NewsFragment

class HomeViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    companion object {
        const val BOOKMARKS_PAGE_INDEX = 0
        const val NEWS_PAGE_INDEX = 1
    }

    private val tabFragmentCreatorsMap: Map<Int, () -> Fragment> = mapOf(
        BOOKMARKS_PAGE_INDEX to { BookmarksFragment() },
        NEWS_PAGE_INDEX to { NewsFragment() }
    )

    override fun getItemCount(): Int {
        return tabFragmentCreatorsMap.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreatorsMap[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}