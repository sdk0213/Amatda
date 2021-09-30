package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.repository.TripRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAllTripUseCase @Inject constructor(private val repository: TripRepository) :
    FlowableUseCase<List<Trip>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<Trip>> {
        return repository.getAllTrip()
    }
}