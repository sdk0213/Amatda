package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InsertCarrierDBUseCase @Inject constructor(private val repository: CarrierRepository) :
    CompletableUseCase<List<CarrierWithPocketAndItems>>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: List<CarrierWithPocketAndItems>?): Completable {
        return repository.insertCarrierDB(params!!)
    }
}