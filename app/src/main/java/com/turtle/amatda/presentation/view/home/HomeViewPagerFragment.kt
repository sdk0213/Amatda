package com.turtle.amatda.presentation.view.home

import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class HomeViewPagerFragment : BaseFragment<HomeViewPagerModel, FragmentHomeBinding>() {

    private val homeFragmentStateAdapter: HomeFragmentStateAdapter // https://stackoverflow.com/questions/56646711/expected-the-adapter-to-be-fresh-while-restoring-state
        get() = HomeFragmentStateAdapter(this)

    private val tabTextList = arrayListOf("Todo", "Setting")

    override fun init() {

        binding.viewPager2.adapter = homeFragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    override fun onDestroyView() {
        binding.viewPager2.adapter = null
        super.onDestroyView()
    }
}
