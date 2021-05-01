package com.turtle.amatda.data.repository.item

import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.ItemRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import com.turtle.amatda.data.mapper.ItemMapper
import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.todo.ItemEntity
import javax.inject.Inject

class ItemRepositoryImpl constructor(
    private val mapper: Mapper<ItemEntity, Item>,
    private val factory: ItemDataSourceFactory
) : ItemRepository {

    override fun getItemAll(): Flowable<List<Item>> {
        return factory.getItemAll().map { list ->
            list.map { item ->
                mapper.mapFromEntity(item)
            }
        }
    }

    override fun insertItem(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.insertItem(itemEntity)
    }

}