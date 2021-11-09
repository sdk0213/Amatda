package com.turtle.amatda.presentation.view.login_sign_in_with_email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.usecases.SignInWithEmailUseCase
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.utilities.AmatdaExceptionMessage
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class LoginSignInWithEmailViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : BaseViewModel() {

    private val _signInSuccess = MutableLiveData<Event<Boolean>>()
    val signInSuccess: LiveData<Event<Boolean>> get() = _signInSuccess

    private val _signInFailedNeedEmailVerified = MutableLiveData<Event<Boolean>>()
    val signInFailedNeedEmailVerified: LiveData<Event<Boolean>> get() = _signInFailedNeedEmailVerified

    private val _signInFailedInvalidPassword = MutableLiveData<Event<Boolean>>()
    val signInFailedInvalidPassword: LiveData<Event<Boolean>> get() = _signInFailedInvalidPassword

    fun signInWithEmail(email: String, password: String) {
        compositeDisposable.add(
            signInWithEmailUseCase.execute(
                ApiCallFirebaseEmailAuth(
                    email = email.replace(" ", ""),
                    password = password
                )
            )
                .subscribe(
                    {
                        _signInSuccess.value = Event(true)
                    },
                    {
                        when(it.message) {
                            AmatdaExceptionMessage.EmailVerificationRequired.exception.message -> _signInFailedNeedEmailVerified.value = Event(true)
                            AmatdaExceptionMessage.InvalidPassword.exception.message -> _signInFailedInvalidPassword.value = Event(true)
                            AmatdaExceptionMessage.ThereIsNoCurrentUser.exception.message -> Timber.e("아마따 어플리케이션의 현재유저(CurrentUser)가 없습니다.")
                            else -> Timber.e(it)
                        }
                    }
                )
        )
    }
}