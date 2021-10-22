package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.TripZoneEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TripZoneDao {

    @Query("SELECT * FROM TripZone Where trip_zone_id == :trip_zone_id")
    fun getTripZone(trip_zone_id: Long): Single<TripZoneEntity>

    @Query("SELECT * FROM TripZone")
    fun getAllTripZone(): Flowable<List<TripZoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTripZone(tripZoneEntity: TripZoneEntity): Completable

    @Delete
    fun deleteTripZone(tripZoneEntity: TripZoneEntity): Completable

    @Update
    fun updateTripZone(tripZoneEntity: TripZoneEntity): Completable
}