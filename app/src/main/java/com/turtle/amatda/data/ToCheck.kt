package com.turtle.amatda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToCheck(@PrimaryKey(autoGenerate = true) val id: Int, var title: String)