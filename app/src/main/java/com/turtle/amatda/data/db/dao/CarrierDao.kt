package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.CarrierAndPocketEntity
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.CarrierWithPocketAndItemsEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CarrierDao {
    @Query("SELECT * FROM Carrier")
    fun getAll(): Flowable<List<CarrierEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carrier: CarrierEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(carrierList: List<CarrierEntity>): Completable

    @Delete
    fun delete(carrier: CarrierEntity): Completable

    @Update
    fun update(carrier: CarrierEntity): Completable

    @Query("DELETE FROM Carrier")
    fun initCarrierDB(): Completable

    /**
     * Carrier With Pocket
     */
    @Transaction
    @Query("SELECT * FROM Carrier")
    fun getCarrierAndPocketData(): Flowable<List<CarrierAndPocketEntity>>

    /**
     * Carrier With Pocket And Items
     */
    @Transaction
    @Query("SELECT * FROM Carrier")
    fun getCarrierWithPocketAndItemsData(): Single<List<CarrierWithPocketAndItemsEntity>>
}