package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.TripEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TripDao {
    @Query("SELECT * FROM Trip")
    fun getAll(): Flowable<List<TripEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tripEntity: TripEntity): Completable

    @Delete
    fun delete(tripEntity: TripEntity): Completable

    @Update
    fun update(tripEntity: TripEntity): Completable

}