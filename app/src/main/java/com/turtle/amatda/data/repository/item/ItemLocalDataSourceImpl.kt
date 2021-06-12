package com.turtle.amatda.data.repository.item

import com.turtle.amatda.data.db.item.ItemDao
import com.turtle.amatda.data.model.todo.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class ItemLocalDataSourceImpl constructor(private val itemDao: ItemDao): ItemLocalDataSource{

    override fun getItemAll(): Flowable<List<ItemEntity>> {
        return itemDao.getAll()
    }

    override fun insertItem(itemEntity: ItemEntity): Completable {
        return itemDao.insert(itemEntity)
    }

    override fun insertItemAll(itemEntity: List<ItemEntity>): Completable {
        return itemDao.insertAll(itemEntity)
    }

}