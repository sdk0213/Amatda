package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.repository.PocketRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SaveCarrierPocketUseCase @Inject constructor(private val repository: PocketRepository) :
    CompletableUseCase<Pocket>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Pocket?): Completable {
        return repository.insertPocket(params!!)
    }
}