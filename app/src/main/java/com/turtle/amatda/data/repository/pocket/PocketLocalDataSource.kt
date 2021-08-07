package com.turtle.amatda.data.repository.pocket

import com.turtle.amatda.data.model.PocketAndItemsEntity
import com.turtle.amatda.data.model.PocketEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface PocketLocalDataSource {

    fun getPocketAll(): Flowable<List<PocketEntity>>
    fun insertPocket(pocketEntity: PocketEntity): Completable
    fun getPocketAndItems() : Flowable<List<PocketAndItemsEntity>>

}