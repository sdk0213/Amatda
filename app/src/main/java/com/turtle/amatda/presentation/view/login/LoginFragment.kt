package com.turtle.amatda.presentation.view.login

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentLoginBinding
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.view.base.BaseFragment
import javax.inject.Inject

class LoginFragment :
    BaseFragment<LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {

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
        binding.btnLoginGoogle.setOnClickListener {
            resultActivity.launch(googleSignInClient.signInIntent)
        }
    }

    private fun observer() {
        viewModel.isLoginSuccess.observe(this@LoginFragment, EventObserver { success ->
            val message = if (success) "로그인 성공" else "로그인 실패"
            showToast(message)
        })

        viewModel.needInternetConnection.observe(this@LoginFragment, EventObserver { needInternetConnection ->
            if(needInternetConnection){
                showToast("인터넷 연결을 확인해주세요")
            }
        })
    }

}