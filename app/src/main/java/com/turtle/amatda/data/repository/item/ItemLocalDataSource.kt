package com.turtle.amatda.data.repository.item

import com.turtle.amatda.data.model.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ItemLocalDataSource {

    fun getItemAll(): Flowable<List<ItemEntity>>
    fun getItemByCarrierId(carrierId: Long) : Flowable<List<ItemEntity>>
    fun insertItem(itemEntity: ItemEntity): Completable
    fun insertItemAll(itemEntity: List<ItemEntity>) : Completable
    fun deleteItem(itemEntity: ItemEntity) : Completable
    fun updateItemName(itemEntity: ItemEntity): Completable
    fun updateItemSize(itemEntity: ItemEntity): Completable
    fun updateItemPos(itemEntity: ItemEntity): Completable
    fun updateItemCount(itemEntity: ItemEntity): Completable
}