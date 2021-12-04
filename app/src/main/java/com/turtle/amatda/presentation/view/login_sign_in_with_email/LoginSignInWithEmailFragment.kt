package com.turtle.amatda.presentation.view.login_sign_in_with_email

import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginSignInWithEmailBinding
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.hideKeyboard
import com.turtle.amatda.presentation.utilities.extensions.isConnected
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
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            findNavController().navigate(
                LoginSignInWithEmailFragmentDirections.actionLoginSignInWithEmailFragmentToLoginSignUpFragment()
            )
        }

        binding.btnLoginEmailCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observer() {
        viewModel.signInSuccess.observe(
            this@LoginSignInWithEmailFragment,
            EventObserver { success ->
                if (success) {
                    showToast(getString(R.string.login_success))
                    mContext.hideKeyboard(binding.btnLoginSignInWithEmailOk.windowToken)
                    findNavController().navigate(
                        LoginSignInWithEmailFragmentDirections.actionLoginSignInWithEmailFragmentToMainViewPagerFragment(
                            "complete"
                        )
                    )
                }
            })

        viewModel.signInFailedNeedEmailVerified.observe(
            this@LoginSignInWithEmailFragment,
            EventObserver { failed ->
                if (failed) {
                    showPopUpMessage(getString(R.string.login_toast_check_verify_email))
                }
            })

        viewModel.signInFailedInvalidPassword.observe(
            this@LoginSignInWithEmailFragment,
            EventObserver { failed ->
                if (failed) {
                    showPopUpMessage(getString(R.string.login_toast_failed_invalid_password))
                }
            })

        viewModel.signInFailedInvalidUser.observe(
            this@LoginSignInWithEmailFragment,
            EventObserver { failed ->
                if (failed) {
                    showPopUpMessage(getString(R.string.login_toast_failed_invalid_user))
                }
            })

        viewModel.signInFailedCurrentIsNull.observe(
            this@LoginSignInWithEmailFragment,
            EventObserver { failed ->
                if (failed) {
                    showPopUpMessage("아마따 어플리케이션의 현재유저(CurrentUser)가 없습니다.")
                }
            }
        )
    }

}