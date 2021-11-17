package com.turtle.amatda.presentation.view.login_sign_in

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginSignInBinding
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.view.base.BaseFragment
import javax.inject.Inject

class LoginSignInFragment :
    BaseFragment<LoginSignInViewModel, FragmentLoginSignInBinding>(R.layout.fragment_login_sign_in) {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val auth = Firebase.auth

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
            findNavController().navigate(
                LoginSignInFragmentDirections.actionLoginSignInFragmentToLoginSignInWithEmailFragment()
            )

        }

        binding.btnLoginGoogle.setOnClickListener {
            resultActivity.launch(googleSignInClient.signInIntent)
        }

        binding.btnLoginKakao.setOnClickListener {
            showToast(getString(R.string.toast_message_failed_this_service_is_not_open))
        }
    }

    private fun observer() {
        viewModel.isLoginSuccess.observe(this@LoginSignInFragment, EventObserver { success ->
            val message = if (success) "로그인 성공" else "로그인 실패"
            showToast(message)
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