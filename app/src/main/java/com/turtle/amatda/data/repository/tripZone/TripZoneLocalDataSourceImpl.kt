package com.turtle.amatda.data.repository.tripZone

import com.turtle.amatda.data.db.dao.TripZoneDao
import com.turtle.amatda.data.model.TripZoneEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TripZoneLocalDataSourceImpl @Inject constructor(
    private val tripZoneDao: TripZoneDao
) : TripZoneLocalDataSource {

    override fun getTripZone(tripZoneEntity: TripZoneEntity): Single<TripZoneEntity> {
        return tripZoneDao.getTripZone(tripZoneEntity.id)
    }

    override fun insertTripZone(tripZoneEntity: TripZoneEntity): Completable {
        return tripZoneDao.insertTripZone(tripZoneEntity)
    }

    override fun deleteTripZone(tripZoneEntity: TripZoneEntity): Completable {
        return tripZoneDao.deleteTripZone(tripZoneEntity)
    }

    override fun updateTripZone(tripZoneEntity: TripZoneEntity): Completable {
        return tripZoneDao.updateTripZone(tripZoneEntity)
    }

}