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

}