package com.turtle.amatda.presentation.view.intro

import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentIntroBinding
import com.turtle.amatda.presentation.android.shard_pref.SharedPrefUtil
import com.turtle.amatda.presentation.view.base.BaseFragment
import javax.inject.Inject

class IntroFragment :
    BaseFragment<IntroViewModel, FragmentIntroBinding>(R.layout.fragment_intro) {

    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil

    override fun init() {
        view()
        viewModel()
    }

    private fun view() {
        handler.postDelayed({
            // 로그인된 기기라면 로그인 과정 생략
            if(sharedPrefUtil.isLoggedDevices){
                // todo: 로그인 과정 생략 한번에 넘어가기 DeepLink
                findNavController().navigate(
                    IntroFragmentDirections.actionIntroFragmentToMainFragment(
                        "complete"
                    )
                )
            } else {
                findNavController().navigate(
                    IntroFragmentDirections.actionIntroFragmentToLoginFragment()
                )
            }
        }, if(sharedPrefUtil.isLoggedDevices) 1000 else 2000)
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }
}