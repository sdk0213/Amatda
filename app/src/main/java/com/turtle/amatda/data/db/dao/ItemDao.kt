package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): Flowable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: List<ItemEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ItemEntity): Completable

    @Delete
    fun delete(item: ItemEntity): Completable

    @Update
    fun update(item: ItemEntity): Completable
}