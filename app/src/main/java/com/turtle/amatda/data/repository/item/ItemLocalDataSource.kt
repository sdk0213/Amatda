package com.turtle.amatda.data.repository.item

import com.turtle.amatda.data.model.todo.ItemEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ItemLocalDataSource {

    fun getItemAll(): Flowable<List<ItemEntity>>
    fun insertItem(itemEntity: ItemEntity): Completable
    fun insertItemAll(itemEntity: List<ItemEntity>) : Completable

}