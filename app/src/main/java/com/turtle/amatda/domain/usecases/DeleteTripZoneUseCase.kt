package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.repository.TripZoneRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteTripZoneUseCase @Inject constructor(private val repository: TripZoneRepository) :
    CompletableUseCase<TripZone>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: TripZone?): Completable {
        return repository.deleteTripZone(params!!)
    }
}