package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAllCarrierUseCase @Inject constructor(private val repository: CarrierRepository) :
    FlowableUseCase<List<CarrierWithPocketAndItems>, Nothing>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<CarrierWithPocketAndItems>> {
        return repository.getObserveCarrierWithPocketAndItems()
    }
}