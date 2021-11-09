package com.turtle.amatda.presentation.view.login_sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.usecases.SignUpWithEmailUseCase
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class LoginSignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase
) : BaseViewModel() {

    private val _signUpSuccess = MutableLiveData<Event<Boolean>>()
    val signUpSuccess: LiveData<Event<Boolean>> get() = _signUpSuccess

    private val _checkPasswordWeakness = MutableLiveData<Event<Boolean>>()
    val checkPasswordWeakness: LiveData<Event<Boolean>> get() = _checkPasswordWeakness

    private val _checkPassword = MutableLiveData<Event<Boolean>>()
    val checkPassword: LiveData<Event<Boolean>> get() = _checkPassword

    fun signUpWithEmail(email: String, password: String, passwordRecheck: String) {
        if (password != passwordRecheck) {
            _checkPassword.value = Event(true)
            return
        }
        compositeDisposable.add(
            signUpWithEmailUseCase.execute(
                ApiCallFirebaseEmailAuth(
                    email = email.replace(" ", ""),
                    password = password
                )
            )
                .subscribe(
                    {
                        _signUpSuccess.value = Event(true)
                    },
                    {
                        if(it is FirebaseAuthWeakPasswordException){
                            _checkPasswordWeakness.value = Event(true)
                        }
                        else {
                            _signUpSuccess.value = Event(false)
                        }
                    }
                )
        )
    }
}