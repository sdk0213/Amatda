package com.turtle.amatda.data.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")  val Id: Long,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "subtitle") val subTitle: String,
)