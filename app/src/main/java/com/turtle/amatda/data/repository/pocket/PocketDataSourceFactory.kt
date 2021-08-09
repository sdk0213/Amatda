package com.turtle.amatda.data.repository.pocket

import com.turtle.amatda.data.model.PocketAndItemsEntity
import com.turtle.amatda.data.model.PocketEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class PocketDataSourceFactory @Inject constructor(
    private val localDataSource: PocketLocalDataSource
) {
    fun getPocketAll() : Flowable<List<PocketEntity>> {
        return localDataSource.getPocketAll()
    }

    fun insertPocket(pocketEntity: PocketEntity) : Completable {
        return localDataSource.insertPocket(pocketEntity)
    }

    fun deletePocket(pocketEntity: PocketEntity) : Completable {
        return localDataSource.deletePocket(pocketEntity)
    }

    fun updatePocketName(pocketEntity: PocketEntity) : Completable {
        return localDataSource.updatePocketName(pocketEntity)
    }

    fun getPocketAndItems(carrierId: Long) : Flowable<List<PocketAndItemsEntity>> {
        return localDataSource.getPocketAndItems(carrierId)
    }
}