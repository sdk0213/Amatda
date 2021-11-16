package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.PocketAndItemsEntity
import com.turtle.amatda.data.model.PocketEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

@Dao
interface PocketDao {
    @Query("SELECT * FROM Pocket")
    fun getAll(): Flowable<List<PocketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pocket: PocketEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pocketList: List<PocketEntity>): Completable

    @Query("UPDATE Pocket SET pocket_name = :pocketName WHERE pocket_id= :pocketId")
    fun updatePocketName(pocketId: Date, pocketName: String): Completable

    @Delete
    fun delete(pocket: PocketEntity): Completable

    @Update
    fun update(pocket: PocketEntity): Completable

    /**
     * Pocket With Item
     */
    @Transaction
    @Query("SELECT * FROM Pocket WHERE carrier_id_foreign = :carrierId")
    fun getPocketAndItemData(carrierId: Long): Flowable<List<PocketAndItemsEntity>>
}