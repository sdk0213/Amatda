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
interface TripDao {

    @Transaction
    @Query("SELECT * FROM Trip")
    fun getAll(): Flowable<List<TripAndTripZoneEntity>>

    @Query("SELECT * FROM Trip Where trip_id == :trip_id")
    fun getTrip(trip_id: Long) : Flowable<TripAndTripZoneEntity>

    fun insert(tripEntity: TripEntity, tripZoneListEntity: List<TripZoneEntity>): Completable {
        return insertTrip(tripEntity).andThen(
            insertTripZone(tripZoneListEntity)
        )
    }

    // Begin: Trip
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrip(tripEntity: TripEntity): Completable

    @Delete
    fun delete(tripEntity: TripEntity): Completable

    @Update
    fun update(tripEntity: TripEntity): Completable
    // End: Trip

    // Begin: TripZone
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTripZone(tripZoneListEntity: List<TripZoneEntity>): Completable
    // End: TripZone
}