package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.ApiCallFirebase
import com.turtle.amatda.domain.repository.FirebaseRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginToFirebaseAuthUseCase @Inject constructor(private val repository: FirebaseRepository) :
    SingleUseCase<Boolean, ApiCallFirebase>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: ApiCallFirebase?): Single<Boolean> {
        return repository.loginUser(params!!)

    }
}