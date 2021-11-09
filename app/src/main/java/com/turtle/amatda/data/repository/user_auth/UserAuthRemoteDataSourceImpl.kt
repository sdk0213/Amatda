package com.turtle.amatda.data.repository.user_auth

import com.turtle.amatda.data.api.FirebaseAuthApiService
import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import io.reactivex.Completable
import javax.inject.Inject

class UserAuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuthApiService: FirebaseAuthApiService
) : UserAuthRemoteDataSource {

    override fun signInWithGoogle(apiCallFirebaseGoogleAuth: ApiCallFirebaseGoogleAuth): Completable {
        return firebaseAuthApiService.signInWithGoogle(apiCallFirebaseGoogleAuth.tokenId)
    }

    override fun signInWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth): Completable {
        return firebaseAuthApiService.signInWithEmail(
            email = apiCallFirebaseEmailAuth.email,
            password = apiCallFirebaseEmailAuth.password
        )
    }

    override fun signUpWithEmail(apiCallFirebaseEmailAuth: ApiCallFirebaseEmailAuth): Completable {
        return firebaseAuthApiService.signUpWithEmail(
            email = apiCallFirebaseEmailAuth.email,
            password = apiCallFirebaseEmailAuth.password
        )
    }
}