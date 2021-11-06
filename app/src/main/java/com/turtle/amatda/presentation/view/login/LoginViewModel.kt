package com.turtle.amatda.presentation.view.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    private val _needInternetConnection = MutableLiveData<Event<Boolean>>()
    val needInternetConnection: LiveData<Event<Boolean>> get() = _needInternetConnection

    private val _isLoginSuccess = MutableLiveData<Event<Boolean>>()
    val isLoginSuccess: LiveData<Event<Boolean>> get() = _isLoginSuccess

    fun googleLogin(intent: Intent){
        try {
            val acct = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException::class.java)
            val credential =
                GoogleAuthProvider.getCredential(acct.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // 로그인 성공
                        _isLoginSuccess.value = Event(true)
                    }
                }
                .addOnFailureListener {
                    if(it is FirebaseNetworkException){
                        // 로그인 실패
                        _needInternetConnection.value = Event(true)
                    }
                    Timber.e("Google Login failed message ${it.message}")
                }
        } catch (apiException: ApiException) {
            Timber.e("Google Login failed message apiException : ${apiException.message}")
            // 로그인 실패
            _isLoginSuccess.value = Event(false)
        }
    }
}