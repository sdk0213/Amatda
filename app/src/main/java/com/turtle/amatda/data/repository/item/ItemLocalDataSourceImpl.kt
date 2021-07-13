package com.turtle.amatda.data.repository.item

import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.model.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class ItemLocalDataSourceImpl constructor(private val itemDao: ItemDao): ItemLocalDataSource{

    override fun getItemAll(): Flowable<List<ItemEntity>> {
        return itemDao.getAll()
    }

    override fun getItemByCarrierId(carrierId: Long): Flowable<List<ItemEntity>> {
        return itemDao.getItemByCarrierId(carrierId)
    }

    override fun insertItem(itemEntity: ItemEntity): Completable {
        return itemDao.insert(itemEntity)
    }

    override fun insertItemAll(itemEntity: List<ItemEntity>): Completable {
        return itemDao.insertAll(itemEntity)
    }

    override fun deleteItem(itemEntity: ItemEntity): Completable {
        return itemDao.delete(itemEntity)
    }

    override fun updateItemName(itemEntity: ItemEntity): Completable {
        return itemDao.updateItemName(item_id = itemEntity.id, item_name = itemEntity.name)
    }

    override fun updateItemSize(itemEntity: ItemEntity): Completable {
        return itemDao.updateItemSize(item_id = itemEntity.id, width = itemEntity.item_width, height = itemEntity.item_height )
    }

    override fun updateItemPos(itemEntity: ItemEntity): Completable {
        return itemDao.updateItemPos(item_id = itemEntity.id, pos_x = itemEntity.position_x, pos_y = itemEntity.position_y)
    }

    override fun updateItemCount(itemEntity: ItemEntity): Completable {
        return itemDao.updateItemCount(item_id = itemEntity.id, count = itemEntity.count)
    }

}