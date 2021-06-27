package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.CarrierAndItemsEntity
import com.turtle.amatda.data.model.CarrierEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CarrierDao {
    @Query("SELECT * FROM Carrier")
    fun getAll(): Flowable<List<CarrierEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carrier: CarrierEntity): Completable

    @Delete
    fun delete(carrier: CarrierEntity): Completable

    @Update
    fun update(carrier: CarrierEntity): Completable

    /**
     * Carrier With Items
     */
    @Transaction
    @Query("SELECT * FROM Carrier")
    fun getCarrierAndItemsData(): Flowable<List<CarrierAndItemsEntity>>
}