package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Experience
import com.turtle.amatda.domain.repository.UserRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateUserExperienceUseCase @Inject constructor(private val repository: UserRepository) :
    CompletableUseCase<Experience>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Experience?): Completable {
        return repository.updateUserExp(params!!)

    }
}