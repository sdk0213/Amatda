package com.turtle.amatda.data.repository.fireBase

import com.turtle.amatda.domain.model.ApiCallFirebase
import io.reactivex.Single
import javax.inject.Inject

class FirebaseDataSourceFactory @Inject constructor(
    private val remoteDataSource: FirebaseRemoteDataSource
) {

    fun loginUser(
        apiCallFirebase: ApiCallFirebase
    ): Single<Boolean> {
        return remoteDataSource.loginUser(apiCallFirebase)
    }
}