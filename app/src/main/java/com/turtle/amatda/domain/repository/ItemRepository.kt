package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Item
import io.reactivex.Completable
import io.reactivex.Flowable

interface ItemRepository {
    fun getItemAll() : Flowable<List<Item>>
    fun getItemsByCarrierId(carrierId: Long) : Flowable<List<Item>>
    fun insertItem(item: Item) : Completable
    fun insertItemAll(itemList: List<Item>) : Completable
    fun deleteItem(item: Item) : Completable
    fun updateItemName(item: Item) : Completable
    fun updateItemSize(item: Item) : Completable
    fun updateItemPos(item: Item): Completable
}