package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteCarrierUseCase @Inject constructor(private val repository: CarrierRepository) :
    CompletableUseCase<Carrier>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Carrier?): Completable {
        return repository.deleteCarrier(params!!)
    }
}