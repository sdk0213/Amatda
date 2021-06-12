package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Item
import io.reactivex.Completable
import io.reactivex.Flowable

interface ItemRepository {
    fun getItemAll() : Flowable<List<Item>>
    fun insertItem(item: Item) : Completable
    fun insertItemAll(itemList: List<Item>) : Completable
}