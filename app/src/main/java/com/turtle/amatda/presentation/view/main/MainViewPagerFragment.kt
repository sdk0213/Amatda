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
            // navigateUp() 사용이유 :
            // navigation component 으로 main 으로 다시 돌아왔을때 기존것 위에 새로운 Fragment 가 생성되는문제로 navigateUp() 진행
            // (기존 fragment 를 재활용하지 않고 새롭게 생성되어 발생하는 문제)
            // single top 으로 진행할경우에는 기존 fragment 의 viewpager2 가 초기화되는 문제가 있어서 사용자 경험이 떨어지기에 사용하지 않음
            // 그러므로 위에 동일한 main 프래그먼트를 더 띄우게끔 view 가 생성될때마다 navigateUp 을 사용하여 항상 이전의 사용했던 Fragment 를 사용하게끔 변경
            // 그래서 위와 같이 main 을 구성하기 위해서는 main 을 목적지로한 action 이 있을경우에 항상 main 이전의 백스택이 없도록 구성
            // todo: Navigation Component 를 사용하고 Action 을 사용하여 이동시 기존 Fragment 가 있다면 새로운 Fragment 를 만들지 않고 기존 Fragment 를 재활용할수 있는 방법 연구 필요
            //          1. singleTop -> 불가능 (기존것 삭제됨)
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
