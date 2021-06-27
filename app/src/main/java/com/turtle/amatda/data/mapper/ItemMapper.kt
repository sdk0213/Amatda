package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.domain.model.Item
import javax.inject.Inject

open class ItemMapper @Inject constructor(): Mapper<ItemEntity, Item> {

    override fun entityToMap(type: ItemEntity): Item {
        return Item(
            id = type.id,
            name = type.name,
            position_x = type.position_x,
            position_y = type.position_y,
            priority = type.priority,
            checked = type.checked,
            carrier_id = type.carrier_id
        )
    }

    override fun mapToEntity(type: Item): ItemEntity {
        return ItemEntity(
            id = type.id,
            name = type.name,
            position_x = type.position_x,
            position_y = type.position_y,
            priority = type.priority,
            checked = type.checked,
            carrier_id = type.carrier_id
        )
    }

}