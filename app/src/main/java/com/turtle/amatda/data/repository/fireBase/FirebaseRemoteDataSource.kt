package com.turtle.amatda.data.repository.fireBase

import com.turtle.amatda.domain.model.ApiCallFirebase
import io.reactivex.Single

interface FirebaseRemoteDataSource {
    fun loginUser(apiCallFirebase: ApiCallFirebase): Single<Boolean>
}