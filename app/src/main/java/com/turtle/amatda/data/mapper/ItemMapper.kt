package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.domain.model.Item
import javax.inject.Inject

open class ItemMapper @Inject constructor() : Mapper<ItemEntity, Item> {

    override fun entityToMap(type: ItemEntity): Item {
        return Item(
            id = type.id,
            name = type.name,
            count = type.count,
            color = type.color,
            position_x = type.position_x,
            position_y = type.position_y,
            width = type.item_width,
            height = type.item_height,
            priority = type.priority,
            checked = type.checked,
            item_place = type.item_place,
            carrier_id = type.carrier_id
        )
    }

    override fun mapToEntity(type: Item): ItemEntity {
        return ItemEntity(
            id = type.id,
            name = type.name,
            count = type.count,
            color = type.color,
            position_x = type.position_x,
            position_y = type.position_y,
            item_width = type.width,
            item_height = type.height,
            priority = type.priority,
            checked = type.checked,
            item_place = type.item_place,
            carrier_id = type.carrier_id
        )
    }

}