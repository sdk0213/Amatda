package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.TripAndTripZoneEntity
import com.turtle.amatda.data.model.TripEntity
import com.turtle.amatda.data.model.TripZoneEntity
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripZone
import javax.inject.Inject

open class TripMapper @Inject constructor() : Mapper<TripAndTripZoneEntity, Trip> {

    override fun entityToMap(type: TripAndTripZoneEntity): Trip {
        return Trip(
            id = type.tripEntity.id,
            title = type.tripEntity.title,
            type = type.tripEntity.type,
            zoneList = type.tripZoneEntityList.map {
                TripZone(
                    id = it.id,
                    area = it.area,
                    addr = it.addr,
                    title = it.title,
                    date = it.date,
                    lat = it.lat,
                    lon = it.lon,
                    zoneType = it.zoneType,
                )
            },
            nightsAndDays = type.tripEntity.nightsAndDays,
            date_start = type.tripEntity.date_start,
            date_end = type.tripEntity.date_end,
            rating = type.tripEntity.rating
        )
    }

    override fun mapToEntity(type: Trip): TripAndTripZoneEntity {
        return TripAndTripZoneEntity(
            tripEntity = TripEntity(
                id = type.id,
                title = type.title,
                type = type.type,
                nightsAndDays = type.nightsAndDays,
                date_start = type.date_start,
                date_end = type.date_end,
                rating = type.rating
            ),
            tripZoneEntityList = type.zoneList.map {
                TripZoneEntity(
                    id = it.id,
                    area = it.area,
                    addr = it.addr,
                    title = it.title,
                    date = it.date,
                    lat = it.lat,
                    lon = it.lon,
                    zoneType = it.zoneType,
                    trip_id = type.id
                )
            }
        )

    }

}