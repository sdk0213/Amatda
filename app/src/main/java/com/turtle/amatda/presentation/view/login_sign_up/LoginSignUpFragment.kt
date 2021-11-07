package com.turtle.amatda.presentation.view.login_sign_up

import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginSignUpBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class LoginSignUpFragment :
    BaseFragment<LoginSignUpViewModel, FragmentLoginSignUpBinding>(R.layout.fragment_login_sign_up) {

    override fun init() {
        view()
        viewModel()
        listener()
        observer()
    }

    private fun view() {

    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun listener() {

    }

    private fun observer() {

    }

}