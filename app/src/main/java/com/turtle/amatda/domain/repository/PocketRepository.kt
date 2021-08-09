package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.model.PocketAndItem
import io.reactivex.Completable
import io.reactivex.Flowable

interface PocketRepository {
    fun getUserPocketAll() : Flowable<List<Pocket>>
    fun insertPocket(pocket: Pocket) : Completable
    fun deletePocket(pocket: Pocket) : Completable
    fun updatePocketName(pocket: Pocket) : Completable
    fun getPocketAndItem(carrierId: Long) : Flowable<List<PocketAndItem>>
}