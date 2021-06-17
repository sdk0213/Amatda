package com.turtle.amatda.data.db.dao

import androidx.room.*
import com.turtle.amatda.data.model.todo.TodoEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ToDoDao {
    @Query("SELECT * FROM TodoEntity")
    fun getAll(): Flowable<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: TodoEntity): Completable

    @Delete
    fun delete(todo: TodoEntity): Completable

    @Update
    fun update(todo: TodoEntity): Completable
}