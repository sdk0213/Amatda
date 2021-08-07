package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.PocketEntity
import com.turtle.amatda.domain.model.Pocket
import javax.inject.Inject

open class PocketMapper @Inject constructor() : Mapper<PocketEntity, Pocket> {

    override fun entityToMap(type: PocketEntity): Pocket {
        return Pocket(
            id = type.id,
            name = type.name,
            carrier_id =  type.carrier_id
        )
    }

    override fun mapToEntity(type: Pocket): PocketEntity {
        return PocketEntity(
            id = type.id,
            name = type.name,
            carrier_id =  type.carrier_id
        )
    }

}