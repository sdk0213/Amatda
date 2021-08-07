package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.PocketAndItemsEntity
import com.turtle.amatda.data.model.PocketEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PocketDao {
    @Query("SELECT * FROM Pocket")
    fun getAll(): Flowable<List<PocketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pocket: PocketEntity): Completable

    @Delete
    fun delete(pocket: PocketEntity): Completable

    @Update
    fun update(pocket: PocketEntity): Completable

    /**
     * Pocket With Item
     */
    @Transaction
    @Query("SELECT * FROM Pocket")
    fun getPocketAndItemData(): Flowable<List<PocketAndItemsEntity>>
}