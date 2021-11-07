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
//            auth.currentUser?.reload()
//            auth.signInWithEmailAndPassword("sdk0213@naver.com", "operatq23!")
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//
//                    } else {
//                        auth.createUserWithEmailAndPassword("sdk0213@naver.com", "operat1q23!")
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//                                    Timber.d("가입 완료")
//                                }
//                            }
//                    }
//                }
//                .addOnFailureListener {
//                    Timber.d("가입완료된 이메일 입니다.")
//                    auth.currentUser?.let {
//                        if (it.isEmailVerified) {
//                            Timber.d("이미 이메일 인증 완료")
//                            return@addOnFailureListener
//                        } else {
//                            Timber.d("이메일 인증 필요")
//                            it.sendEmailVerification()
//                                .addOnCompleteListener { task ->
//                                    if (task.isSuccessful) {
//                                        Timber.d("인증 이메일 전송")
//                                    } else {
//                                        Timber.e("인증 이메일 전송 실패")
//                                    }
//                                }
//                        }
//
//                    }
//                }

        }

        binding.btnLoginGoogle.setOnClickListener {
            resultActivity.launch(googleSignInClient.signInIntent)
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