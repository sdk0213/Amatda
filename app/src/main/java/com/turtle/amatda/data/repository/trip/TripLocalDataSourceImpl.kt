package com.turtle.amatda.data.repository.trip

import com.turtle.amatda.data.db.dao.TripDao
import com.turtle.amatda.data.model.TripEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TripLocalDataSourceImpl @Inject constructor(
    private val tripDao: TripDao
) : TripLocalDataSource {

    override fun getTripAll(): Flowable<List<TripEntity>> {
        return tripDao.getAll()
    }

    override fun insertTrip(tripEntity: TripEntity): Completable {
        return tripDao.insert(tripEntity)
    }

    override fun deleteTrip(tripEntity: TripEntity): Completable {
        return tripDao.delete(tripEntity)
    }

    override fun updateTrip(tripEntity: TripEntity): Completable {
        return tripDao.update(tripEntity)
    }

}