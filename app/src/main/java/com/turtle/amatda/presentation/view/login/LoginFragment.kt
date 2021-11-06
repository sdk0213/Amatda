package com.turtle.amatda.presentation.view.login

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class LoginFragment :
    BaseFragment<LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {

    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {

            } else if (result?.resultCode == Activity.RESULT_CANCELED) {

            }
        }

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