package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.todo.ItemEntity
import com.turtle.amatda.domain.model.Item
import javax.inject.Inject

open class ItemMapper @Inject constructor(): Mapper<ItemEntity, Item> {

    override fun mapFromEntity(type: ItemEntity): Item {
        return Item(id = type.id, name = type.name)
    }

    override fun mapToEntity(type: Item): ItemEntity {
        return ItemEntity(id = type.id, name = type.name)
    }

}