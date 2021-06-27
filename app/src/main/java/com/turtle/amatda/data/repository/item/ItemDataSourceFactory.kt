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

    fun insertItem(itemEntity: ItemEntity) : Completable {
        return localDataSource.insertItem(itemEntity)
    }

    fun insertItemAll(itemEntity: List<ItemEntity>) : Completable {
        return localDataSource.insertItemAll(itemEntity)
    }
}