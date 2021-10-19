package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.TripZone
import io.reactivex.Completable
import io.reactivex.Single

interface TripZoneRepository {
    fun getTripZone(tripZone: TripZone): Single<TripZone>
    fun insertTripZone(tripZone: TripZone): Completable
    fun deleteTripZone(tripZone: TripZone): Completable
    fun updateTripZone(tripZone: TripZone): Completable
}