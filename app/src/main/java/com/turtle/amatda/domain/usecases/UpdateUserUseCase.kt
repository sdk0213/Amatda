package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.User
import com.turtle.amatda.domain.repository.UserRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val repository: UserRepository) :
    CompletableUseCase<User>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: User?): Completable {
        return repository.updateUser(params!!)
    }
}