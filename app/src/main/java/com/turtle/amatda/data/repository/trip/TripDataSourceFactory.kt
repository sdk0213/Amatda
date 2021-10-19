package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.model.TripAndTripZoneEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TripDataSourceFactory @Inject constructor(
    private val localDataSource: TripLocalDataSource
) {
    fun getTripAll(): Flowable<List<TripAndTripZoneEntity>> {
        return localDataSource.getTripAll()
    }

    fun getTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Flowable<TripAndTripZoneEntity> {
        return localDataSource.getTrip(tripAndTripZoneEntity)
    }

    fun insertTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable {
        return localDataSource.insertTrip(tripAndTripZoneEntity)
    }

    fun deleteTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable {
        return localDataSource.deleteTrip(tripAndTripZoneEntity)
    }

    fun updateTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable {
        return localDataSource.updateTrip(tripAndTripZoneEntity)
    }
}