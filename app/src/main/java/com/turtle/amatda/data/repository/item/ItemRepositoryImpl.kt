package com.turtle.amatda.data.repository.item

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.ItemRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class ItemRepositoryImpl constructor(
    private val mapper: Mapper<ItemEntity, Item>,
    private val factory: ItemDataSourceFactory
) : ItemRepository {

    override fun getItemAll(): Flowable<List<Item>> {
        return factory.getItemAll().map { list ->
            list.map { item ->
                mapper.entityToMap(item)
            }
        }
    }

    override fun getItemsByPocketId(pocketId: Date): Flowable<List<Item>> {
        return factory.getItemByPocketId(pocketId).map { list ->
            list.map { item ->
                mapper.entityToMap(item)
            }
        }
    }

    override fun insertItem(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.insertItem(itemEntity)
    }

    override fun insertItemAll(itemList: List<Item>): Completable {
        val itemEntity = itemList.map { list ->
            mapper.mapToEntity(list)
        }
        return factory.insertItemAll(itemEntity)
    }

    override fun deleteItem(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.deleteItem(itemEntity)
    }

    override fun updateItemName(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.updateItemName(itemEntity)
    }

    override fun updateItemSize(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.updateItemSize(itemEntity)
    }

    override fun updateItemPos(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.updateItemPos(itemEntity)
    }

    override fun updateItemCount(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.updateItemCount(itemEntity)
    }

    override fun updateItemColor(item: Item): Completable {
        val itemEntity = mapper.mapToEntity(item)
        return factory.updateItemColor(itemEntity)
    }

}