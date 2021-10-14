package com.turtle.amatda.data.db

import androidx.room.TypeConverter
import com.turtle.amatda.domain.model.TripConcept

class TripConceptEnumTypeConverter {

    @TypeConverter
    fun toEnum(value: String) = enumValueOf<TripConcept>(value)

    @TypeConverter
    fun toString(value: TripConcept) = value.name
}