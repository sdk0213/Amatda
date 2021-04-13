package com.turtle.amatda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val doId: Int,
    var title: String,
    var description: String,
    var checked: Boolean)