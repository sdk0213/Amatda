package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.TripEntity
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.repository.TripRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripMapper: Mapper<TripEntity, Trip>,
    private val factory: TripDataSourceFactory
) : TripRepository {

    override fun getAllTrip(): Flowable<List<Trip>> {
        return factory.getTripAll().map { list ->
            list.map { trip ->
                tripMapper.entityToMap(trip)
            }
        }
    }

    override fun insertTrip(trip: Trip): Completable {
        val tripEntity = tripMapper.mapToEntity(trip)
        return factory.insertTrip(tripEntity)
    }

    override fun deleteTrip(trip: Trip): Completable {
        val tripEntity = tripMapper.mapToEntity(trip)
        return factory.deleteTrip(tripEntity)
    }

    override fun updateTrip(trip: Trip): Completable {
        val tripEntity = tripMapper.mapToEntity(trip)
        return factory.updateTrip(tripEntity)
    }
}