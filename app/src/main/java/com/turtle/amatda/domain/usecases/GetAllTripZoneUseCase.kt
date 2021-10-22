package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.repository.TripZoneRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAllTripZoneUseCase @Inject constructor(private val repository: TripZoneRepository) :
    FlowableUseCase<List<TripZone>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<TripZone>> {
        return repository.getAllTripZone()
    }
}