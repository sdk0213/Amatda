package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.repository.TripRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SaveTripUseCase @Inject constructor(private val repository: TripRepository) :
    CompletableUseCase<Trip>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Trip?): Completable {
        return repository.insertTrip(params!!)
    }
}