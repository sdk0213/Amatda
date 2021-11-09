package com.turtle.amatda.data.repository.user_auth

import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import io.reactivex.Completable
import javax.inject.Inject

class UserAuthDataSourceFactory @Inject constructor(
    private val remoteDataSource: UserAuthRemoteDataSource
) {

    fun signInWithGoogle(
        apiCallFirebaseGoogleAuth: ApiCallFirebaseGoogleAuth
    ): Completable {
        return remoteDataSource.signInWithGoogle(apiCallFirebaseGoogleAuth)
    }

    fun signInWithEmail(
        apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth
    ): Completable {
        return remoteDataSource.signInWithEmail(apiCallFirebaseEmailAuth)
    }

    fun signUpWithEmail(
        apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth
    ) : Completable {
        return remoteDataSource.signUpWithEmail(apiCallFirebaseEmailAuth)
    }
}