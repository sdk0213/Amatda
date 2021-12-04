package com.turtle.amatda.presentation.view.login_sign_in

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import com.turtle.amatda.domain.usecases.SignInWithGoogleUseCase
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class LoginSignInViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : BaseViewModel() {

    private val _needInternetConnection = MutableLiveData<Event<Boolean>>()
    val needInternetConnection: LiveData<Event<Boolean>> get() = _needInternetConnection

    private val _isLoginSuccess = MutableLiveData<Event<Boolean>>()
    val isLoginSuccess: LiveData<Event<Boolean>> get() = _isLoginSuccess

    /**
     * 구글 Firebase 로그인을 요청
     *
     * @param intent 수신받은 데이터 정보
     */
    fun googleLogin(intent: Intent) {
        try {
            val acct = GoogleSignIn.getSignedInAccountFromIntent(intent)
                .getResult(ApiException::class.java)
            acct?.idToken?.let {
                compositeDisposable.add(signInWithGoogleUseCase.execute(
                    ApiCallFirebaseGoogleAuth(
                        tokenId = it
                    )
                )
                    .subscribe(
                        {
                            // 로그인 성공
                            _isLoginSuccess.value = Event(true)
                        },
                        { throwable ->
                            // 로그인 실패 - 기타 에러 발생
                            Log.i("dksung", "${throwable.message}")
                            if (throwable is FirebaseNetworkException) {
                                // 인터넷 연결필요
                                _needInternetConnection.value = Event(true)
                            } else {
                                _isLoginSuccess.value = Event(false)
                            }
                        }
                    )
                )
            } ?: run {
                Timber.e("Google Login failed message: getToken from account is null")
                // 로그인 실패 - 토큰없음
                _isLoginSuccess.value = Event(false)
            }
        } catch (apiException: ApiException) {
            Timber.e("Google Login failed message apiException : ${apiException.message}")
            // 로그인 실패 - apiExcpetion 발생
            _isLoginSuccess.value = Event(false)
        }
    }
}