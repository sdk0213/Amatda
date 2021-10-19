package com.turtle.amatda.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turtle.amatda.domain.model.TripConcept
import java.util.*

@Entity(tableName = "Trip")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trip_id")
    val id: Long = 0,

    @ColumnInfo(name = "trip_name")
    val title: String,

    @ColumnInfo(name = "trip_concept")
    val type: TripConcept = TripConcept.NORMAL,

    @ColumnInfo(name = "trip_nights_and_days")
    val nightsAndDays: String,

    @ColumnInfo(name = "trip_date_start")
    val date_start: Date,

    @ColumnInfo(name = "trip_date_end")
    val date_end: Date,

    @ColumnInfo(name = "trip_rating")
    val rating: Int // ( 0 <= rating <= 5)
)