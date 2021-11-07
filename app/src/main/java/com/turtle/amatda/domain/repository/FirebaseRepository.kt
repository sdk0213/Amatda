package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.ApiCallFirebase
import io.reactivex.Single

interface FirebaseRepository {
    fun loginUser(apiCallFirebase: ApiCallFirebase): Single<Boolean>
}