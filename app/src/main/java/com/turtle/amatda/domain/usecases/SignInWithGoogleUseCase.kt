package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.ApiCallFirebaseGoogleAuth
import com.turtle.amatda.domain.repository.UserAuthRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val repository: UserAuthRepository) :
    CompletableUseCase<ApiCallFirebaseGoogleAuth>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: ApiCallFirebaseGoogleAuth?): Completable {
        return repository.signInWithGoogle(params!!)

    }
}