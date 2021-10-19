package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.TripAndTripZoneEntity
import com.turtle.amatda.data.model.TripEntity
import com.turtle.amatda.data.model.TripZoneEntity
import com.turtle.amatda.domain.model.Trip
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TripZoneDao {

    @Query("SELECT * FROM TripZone Where trip_zone_id == :trip_zone_id")
    fun getTripZone(trip_zone_id: Long) : Single<TripZoneEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTripZone(tripZoneEntity: TripZoneEntity): Completable

    @Delete
    fun deleteTripZone(tripZoneEntity: TripZoneEntity): Completable

    @Update
    fun updateTripZone(tripZoneEntity: TripZoneEntity): Completable
}