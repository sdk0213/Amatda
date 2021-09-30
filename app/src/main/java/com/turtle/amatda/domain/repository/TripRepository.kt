package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Trip
import io.reactivex.Completable
import io.reactivex.Flowable

interface TripRepository {
    fun getAllTrip() : Flowable<List<Trip>>
    fun insertTrip(trip: Trip) : Completable
    fun deleteTrip(trip: Trip) : Completable
    fun updateTrip(trip: Trip) : Completable
}