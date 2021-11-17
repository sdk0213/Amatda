package com.turtle.amatda.presentation.view.login_sign_in

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginSignInBinding
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.hideKeyboard
import com.turtle.amatda.presentation.utilities.extensions.isConnected
import com.turtle.amatda.presentation.view.base.BaseFragment
import javax.inject.Inject

class LoginSignInFragment :
    BaseFragment<LoginSignInViewModel, FragmentLoginSignInBinding>(R.layout.fragment_login_sign_in) {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    viewModel.googleLogin(intent)
                }
            } else if (result?.resultCode == Activity.RESULT_CANCELED) {
                showToast("로그인 실패")
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
        binding.btnLoginAmatda.setOnClickListener {
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            findNavController().navigate(
                LoginSignInFragmentDirections.actionLoginSignInFragmentToLoginSignInWithEmailFragment()
            )

        }

        binding.btnLoginGoogle.setOnClickListener {
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            resultActivity.launch(googleSignInClient.signInIntent)
        }

        binding.btnLoginKakao.setOnClickListener {
            showToast(getString(R.string.toast_message_failed_this_service_is_not_open))
        }
    }

    private fun observer() {
        viewModel.isLoginSuccess.observe(this@LoginSignInFragment, EventObserver { success ->
            if (success) {
                showToast(getString(R.string.login_success))
                mContext.hideKeyboard(binding.btnLoginGoogle.windowToken)
                findNavController().navigate(
                    LoginSignInFragmentDirections.actionLoginSignInFragmentToMainViewPagerFragment(
                        "complete"
                    )
                )
            }
        })

        viewModel.needInternetConnection.observe(
            this@LoginSignInFragment,
            EventObserver { needInternetConnection ->
                if (needInternetConnection) {
                    showToast("인터넷 연결을 확인해주세요")
                }
            })
    }

}