package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.model.TripAndTripZoneEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface TripLocalDataSource {

    fun getTripAll(): Flowable<List<TripAndTripZoneEntity>>
    fun getTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Flowable<TripAndTripZoneEntity>
    fun insertTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable
    fun deleteTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable
    fun updateTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable

}