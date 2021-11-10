package com.turtle.amatda.presentation.view.login_sign_up

import android.view.View
import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginSignUpBinding
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.hideKeyboard
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
        binding.btnLoginEmailOkVerify.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnLoginEmailCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observer() {

        viewModel.checkPassword.observe(this@LoginSignUpFragment, EventObserver { checkPassword ->
            if (checkPassword) {
                showToast(getString(R.string.login_toast_password_recheck))
            }
        })

        viewModel.signUpSuccess.observe(this@LoginSignUpFragment, EventObserver { signUpSuccess ->
            if (signUpSuccess) {
                showToast(getString(R.string.login_toast_sign_up_success))
                mContext.hideKeyboard(binding.btnLoginSignUpWithEmailOk.windowToken)
                binding.tvSignUpVerifyEmail.visibility = View.VISIBLE
                binding.btnLoginEmailOkVerify.visibility = View.VISIBLE
                binding.btnLoginEmailCancel.visibility = View.INVISIBLE
                binding.btnLoginSignUpWithEmailOk.visibility = View.INVISIBLE
                binding.tilLoginEmail.visibility = View.INVISIBLE
                binding.tilLoginPassword.visibility = View.INVISIBLE
                binding.tilLoginPasswordRecheck.visibility = View.INVISIBLE

            } else {
                showToast(getString(R.string.login_toast_sign_up_failed))
            }
        })

        viewModel.checkPasswordWeakness.observe(
            this@LoginSignUpFragment,
            EventObserver { pwWeakness ->
                if (pwWeakness) {
                    showToast(getString(R.string.login_toast_sign_up_failed_pw_weakness))
                }
            })
    }

}