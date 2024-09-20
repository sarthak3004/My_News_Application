package com.sarthak.mynewsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sarthak.mynewsapplication.R
import com.sarthak.mynewsapplication.adapters.HomeViewPagerAdapter
import com.sarthak.mynewsapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val bottomNavigationView = binding.bottomNavigation
        val viewPager = binding.viewPager

        viewPager.adapter = HomeViewPagerAdapter(this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    HomeViewPagerAdapter.BOOKMARKS_PAGE_INDEX -> bottomNavigationView.menu.findItem(
                        R.id.navigation_bookmarks
                    ).isChecked = true

                    HomeViewPagerAdapter.NEWS_PAGE_INDEX -> bottomNavigationView.menu.findItem(R.id.navigation_news).isChecked =
                        true
                }
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_bookmarks -> {
                    viewPager.setCurrentItem(HomeViewPagerAdapter.BOOKMARKS_PAGE_INDEX, true)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_news -> {
                    viewPager.setCurrentItem(HomeViewPagerAdapter.NEWS_PAGE_INDEX, true)
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener true
        }
        return binding.root
    }
}
