package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.db.dao.TripDao
import com.turtle.amatda.data.model.TripAndTripZoneEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TripLocalDataSourceImpl @Inject constructor(
    private val tripDao: TripDao
) : TripLocalDataSource {

    override fun getTripAll(): Flowable<List<TripAndTripZoneEntity>> {
        return tripDao.getAll()
    }

    override fun getTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Flowable<TripAndTripZoneEntity> {
        return tripDao.getTrip(tripAndTripZoneEntity.tripEntity.id)
    }

    override fun insertTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable {
        return tripDao.insert(
            tripAndTripZoneEntity.tripEntity,
            tripAndTripZoneEntity.tripZoneEntityList
        )
    }

    override fun deleteTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable {
        return tripDao.delete(tripAndTripZoneEntity.tripEntity)
    }

    override fun updateTrip(tripAndTripZoneEntity: TripAndTripZoneEntity): Completable {
        return tripDao.update(tripAndTripZoneEntity.tripEntity)
    }

}