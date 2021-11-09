package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import io.reactivex.Completable

interface UserAuthRepository {

    fun signInWithGoogle(apiCallFirebaseGoogleAuth: ApiCallFirebaseGoogleAuth): Completable

    fun signInWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth): Completable

    fun signUpWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth) : Completable


}