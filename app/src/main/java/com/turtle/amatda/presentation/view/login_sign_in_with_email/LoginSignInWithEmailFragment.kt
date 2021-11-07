package com.turtle.amatda.presentation.view.login_sign_in_with_email

import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginSignInWithEmailBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class LoginSignInWithEmailFragment :
    BaseFragment<LoginSignInWithEmailViewModel, FragmentLoginSignInWithEmailBinding>(R.layout.fragment_login_sign_in_with_email) {

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
        binding.btnLoginEmailSignUp.setOnClickListener {
            findNavController().navigate(
                LoginSignInWithEmailFragmentDirections.actionLoginSignInWithEmailFragmentToLoginSignUpFragment()
            )
        }
    }

    private fun observer() {

    }

}