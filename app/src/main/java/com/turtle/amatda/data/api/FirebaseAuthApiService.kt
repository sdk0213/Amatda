package com.turtle.amatda.data.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.turtle.amatda.presentation.utilities.AmatdaExceptionMessage
import io.reactivex.Completable
import javax.inject.Inject

class FirebaseAuthApiService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun signInWithGoogle(tokenId: String): Completable {
        val credential = GoogleAuthProvider.getCredential(tokenId, null)
        return Completable.create { emitter ->
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun signInWithEmail(email: String, password: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.d("dksung", "addOnSuccessListener")
                    firebaseAuth.currentUser?.let {
                        it.reload() // 갱신
                        if (!it.isEmailVerified) {
                            firebaseAuth.currentUser?.let { currentUser ->
                                firebaseAuth.useAppLanguage()
                                currentUser.sendEmailVerification()
                            }
                            emitter.onError(AmatdaExceptionMessage.EmailVerificationRequired.exception)
                            return@addOnSuccessListener
                        } else {
                            emitter.onComplete()
                        }
                    } ?: run {
                        emitter.onError(AmatdaExceptionMessage.ThereIsNoCurrentUser.exception)
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d("dksung", "addOnFailureListener ${exception.message}")
                    when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            emitter.onError(AmatdaExceptionMessage.InvalidPassword.exception)
                        }
                        is FirebaseAuthInvalidUserException -> {
                            emitter.onError(AmatdaExceptionMessage.InvalidPassword.exception)
                        }
                        else -> {
                            emitter.onError(exception)
                        }
                    }
                }
        }
    }


    fun signUpWithEmail(email: String, password: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    firebaseAuth.currentUser?.let { currentUser ->
                        firebaseAuth.useAppLanguage()
                        currentUser.sendEmailVerification()
                            .addOnCompleteListener {
                                emitter.onComplete()
                            }
                            .addOnFailureListener { exception ->
                                emitter.onError(exception)
                            }
                    }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}