package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.User
import com.turtle.amatda.domain.repository.UserRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import com.turtle.amatda.domain.usecases.common.ObservableUseCase
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) :
    ObservableUseCase<User, User>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: User?): Observable<User> {
        return repository.getUser(params!!)
    }
}