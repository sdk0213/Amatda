package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndItems
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetUserCarrierUseCase @Inject constructor(private val repository: CarrierRepository) :
    FlowableUseCase<List<CarrierAndItems>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<CarrierAndItems>> {
        return repository.getCarrierAndItems()
    }

}