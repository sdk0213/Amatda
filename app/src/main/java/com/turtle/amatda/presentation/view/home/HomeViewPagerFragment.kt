package com.turtle.amatda.presentation.view.home

import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class HomeViewPagerFragment : BaseFragment<HomeViewPagerModel, FragmentHomeBinding>() {

    private val homeFragmentStateAdapter: HomeFragmentStateAdapter // DI 사용시에는 다음 stackoverflow 참고 HomeFragmentStateAdapter.get() 형태 , 지금은 사용하지 않음 : https://stackoverflow.com/questions/56646711/expected-the-adapter-to-be-fresh-while-restoring-state
        get() = HomeFragmentStateAdapter(this)

    private val tabTextList
        get() = arrayListOf(getString(R.string.menu_checklist), getString(R.string.menu_date), getString(R.string.menu_setting))

    override fun init() {

        binding.viewPager.adapter = homeFragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}
