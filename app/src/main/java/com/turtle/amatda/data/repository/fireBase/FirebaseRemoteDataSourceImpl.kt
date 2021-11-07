package com.turtle.amatda.data.repository.fireBase

import com.turtle.amatda.data.api.FirebaseAuthApiService
import com.turtle.amatda.domain.model.ApiCallFirebase
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuthApiService: FirebaseAuthApiService
) : FirebaseRemoteDataSource {

    override fun loginUser(apiCallFirebase: ApiCallFirebase): Single<Boolean> {
        return firebaseAuthApiService.loginUser(apiCallFirebase.tokenId)
    }
}