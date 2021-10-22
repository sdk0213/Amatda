package com.turtle.amatda.data.repository.tripZone

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.TripZoneEntity
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.repository.TripZoneRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TripZoneRepositoryImpl @Inject constructor(
    private val mapper: Mapper<TripZoneEntity, TripZone>,
    private val factory: TripZoneDataSourceFactory
) : TripZoneRepository {

    override fun getTripZone(tripZone: TripZone): Single<TripZone> {
        return factory.getTripZone(mapper.mapToEntity(tripZone)) // Entity 으로 변환하여 요청후
            .map { mapper.entityToMap(it) } // 다시 Map 으로 변환하여 전달
    }

    override fun getAllTripZone(): Flowable<List<TripZone>> {
        return factory.getAllTripZone()
            .map { it.map {  tripZoneEntity ->
                mapper.entityToMap(tripZoneEntity)
            } }
    }

    override fun insertTripZone(tripZone: TripZone): Completable {
        val tripEntity = mapper.mapToEntity(tripZone)
        return factory.insertTripZone(tripEntity)
    }

    override fun deleteTripZone(tripZone: TripZone): Completable {
        val tripEntity = mapper.mapToEntity(tripZone)
        return factory.deleteTripZone(tripEntity)
    }

    override fun updateTripZone(tripZone: TripZone): Completable {
        val tripEntity = mapper.mapToEntity(tripZone)
        return factory.updateTripZone(tripEntity)
    }
}