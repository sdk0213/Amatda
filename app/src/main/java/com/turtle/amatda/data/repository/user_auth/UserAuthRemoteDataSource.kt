package com.turtle.amatda.data.repository.user_auth

import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import io.reactivex.Completable

interface UserAuthRemoteDataSource {
    fun signInWithGoogle(apiCallFirebaseGoogleAuth: ApiCallFirebaseGoogleAuth): Completable
    fun signInWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth) : Completable
    fun signUpWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth) : Completable
}