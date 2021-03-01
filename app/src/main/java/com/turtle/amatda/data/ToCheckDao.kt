package com.turtle.amatda.data

import androidx.room.*

@Dao
interface ToCheckDao {
    @Query("SELECT * FROM ToCheck")
    fun getAll(): List<ToCheck>

    @Insert
    fun insert(toCheck: ToCheck)

    @Delete
    fun delete(toCheck: ToCheck)

    @Update
    fun update(toCheck: ToCheck)
}