package com.turtle.amatda.data.repository.user_auth

import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import com.turtle.amatda.domain.repository.UserAuthRepository
import io.reactivex.Completable
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val factory: UserAuthDataSourceFactory
) : UserAuthRepository {

    override fun signInWithGoogle(apiCallFirebaseGoogleAuth: ApiCallFirebaseGoogleAuth): Completable {
        return factory.signInWithGoogle(apiCallFirebaseGoogleAuth)
    }

    override fun signInWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth): Completable {
        return factory.signInWithEmail(apiCallFirebaseEmailAuth)
    }

    override fun signUpWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth): Completable {
        return factory.signUpWithEmail(apiCallFirebaseEmailAuth)
    }

}