package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.model.TripEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TripDataSourceFactory @Inject constructor(
    private val localDataSource: TripLocalDataSource
) {
    fun getTripAll(): Flowable<List<TripEntity>> {
        return localDataSource.getTripAll()
    }

    fun insertTrip(tripEntity: TripEntity): Completable {
        return localDataSource.insertTrip(tripEntity)
    }

    fun deleteTrip(tripEntity: TripEntity): Completable {
        return localDataSource.deleteTrip(tripEntity)
    }

    fun updateTrip(tripEntity: TripEntity): Completable {
        return localDataSource.updateTrip(tripEntity)
    }
}