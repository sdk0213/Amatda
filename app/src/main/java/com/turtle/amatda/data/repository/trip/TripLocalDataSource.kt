package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.model.TripEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface TripLocalDataSource {

    fun getTripAll(): Flowable<List<TripEntity>>
    fun insertTrip(tripEntity: TripEntity): Completable
    fun deleteTrip(tripEntity: TripEntity): Completable
    fun updateTrip(tripEntity: TripEntity): Completable

}