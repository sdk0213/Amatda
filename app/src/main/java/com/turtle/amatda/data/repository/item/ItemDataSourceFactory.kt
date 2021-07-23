package com.turtle.amatda.data.repository.item

import com.turtle.amatda.data.model.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class ItemDataSourceFactory @Inject constructor(
    private val localDataSource: ItemLocalDataSource
){
    fun getItemAll() : Flowable<List<ItemEntity>> {
        return localDataSource.getItemAll()
    }

    fun getItemByCarrierId(carrierId: Long) : Flowable<List<ItemEntity>> {
        return localDataSource.getItemByCarrierId(carrierId)
    }

    fun insertItem(itemEntity: ItemEntity) : Completable {
        return localDataSource.insertItem(itemEntity)
    }

    fun insertItemAll(itemEntity: List<ItemEntity>) : Completable {
        return localDataSource.insertItemAll(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) : Completable {
        return localDataSource.deleteItem(itemEntity)
    }

    fun updateItemName(itemEntity: ItemEntity) : Completable {
        return localDataSource.updateItemName(itemEntity)
    }

    fun updateItemSize(itemEntity: ItemEntity) : Completable {
        return localDataSource.updateItemSize(itemEntity)
    }

    fun updateItemPos(itemEntity: ItemEntity) : Completable {
        return localDataSource.updateItemPos(itemEntity)
    }

    fun updateItemCount(itemEntity: ItemEntity) : Completable {
        return localDataSource.updateItemCount(itemEntity)
    }

    fun updateItemColor(itemEntity: ItemEntity) : Completable {
        return localDataSource.updateItemColor(itemEntity)
    }
}