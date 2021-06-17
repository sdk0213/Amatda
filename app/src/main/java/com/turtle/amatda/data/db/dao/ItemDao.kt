package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.todo.ItemEntity
import com.turtle.amatda.data.model.todo.TodoEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity")
    fun getAll(): Flowable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todo: List<ItemEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: ItemEntity): Completable

    @Delete
    fun delete(todo: ItemEntity): Completable

    @Update
    fun update(todo: ItemEntity): Completable
}