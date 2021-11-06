package com.turtle.amatda.presentation.view.intro

import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentIntroBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class IntroFragment :
    BaseFragment<IntroViewModel, FragmentIntroBinding>(R.layout.fragment_intro) {

    override fun init() {
        view()
        viewModel()
    }

    private fun view() {
        handler.postDelayed({
            findNavController().navigate(
                IntroFragmentDirections.actionIntroFragmentToLoginFragment()
            )
        }, 2000)
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }
}