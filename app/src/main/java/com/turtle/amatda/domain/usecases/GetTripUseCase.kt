package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.repository.TripRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTripUseCase @Inject constructor(private val repository: TripRepository) :
    FlowableUseCase<Trip, Trip>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Trip?): Flowable<Trip> {
        return repository.getTrip(params!!)
    }
}