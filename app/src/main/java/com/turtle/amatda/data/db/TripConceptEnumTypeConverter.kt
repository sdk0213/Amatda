package com.turtle.amatda.data.db

import androidx.room.TypeConverter
import com.turtle.amatda.domain.model.TripConcept

class TripConceptEnumTypeConverter {

    @TypeConverter
    fun toEnum(value: String) = TripConcept.values().find { it.concept == value }

    @TypeConverter
    fun toString(value: TripConcept) = value.concept
}