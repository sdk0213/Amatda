package com.turtle.amatda.presentation.view.main

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMainViewpagerBinding
import com.turtle.amatda.presentation.android.shard_pref.SharedPrefUtil
import com.turtle.amatda.presentation.view.base.BaseFragment
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import javax.inject.Inject

class MainViewPagerFragment :
    BaseFragment<MainViewPagerModel, FragmentMainViewpagerBinding>(R.layout.fragment_main_viewpager) {

    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil

    private val args: MainViewPagerFragmentArgs by navArgs()

    private val mainFragmentStateAdapter: MainFragmentStateAdapter // DI 사용시에는 다음 stackoverflow 참고 HomeFragmentStateAdapter.get() 형태 , 지금은 사용하지 않음 : https://stackoverflow.com/questions/56646711/expected-the-adapter-to-be-fresh-while-restoring-state
        get() = MainFragmentStateAdapter(this)

    private val tabTextList by lazy {
        arrayListOf(
            getString(R.string.menu_home),
            getString(R.string.menu_my_carrier),
            getString(R.string.menu_my_trip),
            getString(R.string.menu_my_page)
        )
    }

    override fun init() {

        binding.viewPager.adapter = mainFragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
        if (args.loginComplete != "complete") {
            // todo: 왜 해당 코드를 사용하지 않으면 MainViewPagerFragment 가 하나 더 띄어지는지 확인이 필요함
            findNavController().navigateUp()
        } else {
            sharedPrefUtil.isLoggedDevices = true
        }

        // 키보드 올라왔을때 TabLayout 에 의하여 가려지는 현상을 방지하기위하여 키보드가 올라왔을대는 TabLayout GONE 처리
        KeyboardVisibilityEvent.setEventListener(
            requireActivity()
        ) { isOpen -> binding.tabLayout.visibility = if (isOpen) View.GONE else View.VISIBLE }
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}
