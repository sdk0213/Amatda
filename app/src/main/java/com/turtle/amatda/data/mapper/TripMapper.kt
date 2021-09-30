package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.TripEntity
import com.turtle.amatda.domain.model.Trip
import javax.inject.Inject

open class TripMapper @Inject constructor() : Mapper<TripEntity, Trip> {

    override fun entityToMap(type: TripEntity): Trip {
        return Trip(
            id = type.id,
            title = type.title,
            course = type.course,
            nightsAndDays = type.nightsAndDays,
            date_start = type.date_start,
            date_end = type.date_end,
            rating = type.rating
        )
    }

    override fun mapToEntity(type: Trip): TripEntity {
        return TripEntity(
            id = type.id,
            title = type.title,
            course = type.course,
            nightsAndDays = type.nightsAndDays,
            date_start = type.date_start,
            date_end = type.date_end,
            rating = type.rating
        )
    }

}