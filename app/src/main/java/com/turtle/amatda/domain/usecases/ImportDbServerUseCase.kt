package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImportDbServerUseCase @Inject constructor(private val repository: CarrierRepository) :
    SingleUseCase<List<CarrierWithPocketAndItems>, Nothing>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: Nothing?): Single<List<CarrierWithPocketAndItems>> {
        return repository.importUserCarrierDbServer()
    }
}