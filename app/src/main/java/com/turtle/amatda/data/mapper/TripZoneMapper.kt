package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.TripZoneEntity
import com.turtle.amatda.domain.model.TripZone
import javax.inject.Inject

open class TripZoneMapper @Inject constructor() : Mapper<TripZoneEntity, TripZone> {

    override fun entityToMap(type: TripZoneEntity): TripZone {
        return TripZone(
            id = type.id,
            area = type.area,
            addr = type.addr,
            title = type.title,
            date = type.date,
            lat = type.lat,
            lon = type.lon,
            zoneType = type.zoneType,
        )
    }

    override fun mapToEntity(type: TripZone): TripZoneEntity {
        return TripZoneEntity(
            id = type.id,
            area = type.area,
            addr = type.addr,
            title = type.title,
            date = type.date,
            lat = type.lat,
            lon = type.lon,
            zoneType = type.zoneType,
            trip_id = type.id
        )

    }

}