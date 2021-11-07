package com.turtle.amatda.data.repository.fireBase

import com.turtle.amatda.domain.model.ApiCallFirebase
import com.turtle.amatda.domain.repository.FirebaseRepository
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val factory: FirebaseDataSourceFactory
) : FirebaseRepository {

    override fun loginUser(apiCallFirebase: ApiCallFirebase): Single<Boolean> {
        return factory.loginUser(apiCallFirebase)
    }

}