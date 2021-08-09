package com.turtle.amatda.data.repository.pocket

import com.turtle.amatda.data.model.PocketAndItemsEntity
import com.turtle.amatda.data.model.PocketEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface PocketLocalDataSource {

    fun getPocketAll(): Flowable<List<PocketEntity>>
    fun insertPocket(pocketEntity: PocketEntity): Completable
    fun deletePocket(pocketEntity: PocketEntity) : Completable
    fun updatePocketName(pocketEntity: PocketEntity) : Completable
    fun getPocketAndItems(carrierId: Long) : Flowable<List<PocketAndItemsEntity>>

}