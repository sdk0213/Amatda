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

    @Query("UPDATE Item SET item_width = :width, item_height = :height WHERE item_id = :item_id")
    fun updateItemSize(item_id: Date, width: Int, height: Int): Completable

    @Query("UPDATE Item SET item_position_x = :pos_x, item_position_y = :pos_y WHERE item_id = :item_id")
    fun updateItemPos(item_id: Date, pos_x: Float, pos_y: Float): Completable

    @Query("UPDATE Item SET item_count = :count WHERE item_id = :item_id")
    fun updateItemCount(item_id: Date, count: Int): Completable

    @Query("UPDATE Item SET item_color = :color WHERE item_id = :item_id")
    fun updateItemColor(item_id: Date, color: Long): Completable
}