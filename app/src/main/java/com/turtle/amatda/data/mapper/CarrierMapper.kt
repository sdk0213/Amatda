package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.domain.model.Carrier
import javax.inject.Inject

open class CarrierMapper @Inject constructor() : Mapper<CarrierEntity, Carrier> {

    override fun entityToMap(type: CarrierEntity): Carrier {
        return Carrier(
            id = type.id,
            name = type.name,
            date = type.date,
            type = type.type,
            size = type.size
        )
    }

    override fun mapToEntity(type: Carrier): CarrierEntity {
        return CarrierEntity(
            id = type.id,
            name = type.name,
            date = type.date,
            type = type.type,
            size = type.size
        )
    }

}