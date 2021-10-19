package com.turtle.amatda.data.repository.tripZone

import com.turtle.amatda.data.model.TripZoneEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TripZoneDataSourceFactory @Inject constructor(
    private val localDataSource: TripZoneLocalDataSource
) {

    fun getTripZone(tripZoneEntity: TripZoneEntity): Single<TripZoneEntity> {
        return localDataSource.getTripZone(tripZoneEntity)
    }

    fun insertTripZone(tripZoneEntity: TripZoneEntity): Completable {
        return localDataSource.insertTripZone(tripZoneEntity)
    }

    fun deleteTripZone(tripZoneEntity: TripZoneEntity): Completable {
        return localDataSource.deleteTripZone(tripZoneEntity)
    }

    fun updateTripZone(tripZoneEntity: TripZoneEntity): Completable {
        return localDataSource.updateTripZone(tripZoneEntity)
    }
}