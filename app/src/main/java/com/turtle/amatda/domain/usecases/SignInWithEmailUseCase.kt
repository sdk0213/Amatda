package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.ApiCallFirebaseEmailAuth
import com.turtle.amatda.domain.repository.UserAuthRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(private val repository: UserAuthRepository) :
    CompletableUseCase<ApiCallFirebaseEmailAuth>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: ApiCallFirebaseEmailAuth?): Completable {
        return repository.signInWithEmail(params!!)

    }
}