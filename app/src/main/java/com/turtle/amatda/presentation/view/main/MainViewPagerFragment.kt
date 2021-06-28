package com.turtle.amatda.presentation.view.main

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMainViewpagerBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class MainViewPagerFragment : BaseFragment<MainViewPagerModel, FragmentMainViewpagerBinding>() {

    private val mainFragmentStateAdapter: MainFragmentStateAdapter // DI 사용시에는 다음 stackoverflow 참고 HomeFragmentStateAdapter.get() 형태 , 지금은 사용하지 않음 : https://stackoverflow.com/questions/56646711/expected-the-adapter-to-be-fresh-while-restoring-state
        get() = MainFragmentStateAdapter(this)

    private val tabTextList
        get() = arrayListOf(getString(R.string.menu_home), getString(R.string.menu_my_carrier), getString(R.string.menu_my_trip), getString(R.string.menu_my_page))

    override fun init() {

        binding.viewPager.adapter = mainFragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}
