package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): Flowable<List<ItemEntity>>

    @Query("SELECT * FROM Item WHERE carrier_id_foreign == :carrierId")
    fun getItemByCarrierId(carrierId: Long) : Flowable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: List<ItemEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ItemEntity): Completable

    @Delete
    fun delete(item: ItemEntity): Completable

    @Update
    fun updateItem(item: ItemEntity): Completable

    @Query("UPDATE Item SET item_name = :item_name WHERE item_id = :item_id")
    fun updateItemName(item_id: Date, item_name: String): Completable
}