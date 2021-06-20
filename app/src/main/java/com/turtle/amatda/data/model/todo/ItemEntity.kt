package com.turtle.amatda.data.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")  val id: Long,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "position_x") val position_x: Int = 0,

    @ColumnInfo(name = "position_y") val position_y: Int = 0,

    @ColumnInfo(name = "priority") val priority: Long = 0,

    @ColumnInfo(name = "checked") val checked : Boolean = false,
)