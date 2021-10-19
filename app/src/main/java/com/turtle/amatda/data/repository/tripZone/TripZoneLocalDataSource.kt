package com.turtle.amatda.data.repository.tripZone

import com.turtle.amatda.data.model.TripZoneEntity
import io.reactivex.Completable
import io.reactivex.Single

interface TripZoneLocalDataSource {

    fun getTripZone(tripZoneEntity: TripZoneEntity): Single<TripZoneEntity>
    fun insertTripZone(tripZoneEntity: TripZoneEntity): Completable
    fun deleteTripZone(tripZoneEntity: TripZoneEntity): Completable
    fun updateTripZone(tripZoneEntity: TripZoneEntity): Completable

}