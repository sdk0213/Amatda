package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.CarrierAndPocket
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetUserCarrierUseCase @Inject constructor(private val repository: CarrierRepository) :
    FlowableUseCase<List<CarrierAndPocket>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<CarrierAndPocket>> {
        return repository.getCarrierAndPocket()
    }

}