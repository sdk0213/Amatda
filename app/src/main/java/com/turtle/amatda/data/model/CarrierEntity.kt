package com.turtle.amatda.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = "Carrier")
data class CarrierEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "carrier_id")
    val id: Long,
    @ColumnInfo(name = "carrier_name")
    val name: String,
    @ColumnInfo(name = "carrier_date")
    val date: Date,
    @ColumnInfo(name = "carrier_type")
    val type: String,
    @ColumnInfo(name = "carrier_size")
    val size: String,
)
