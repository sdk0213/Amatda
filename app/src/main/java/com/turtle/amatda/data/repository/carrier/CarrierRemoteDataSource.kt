package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.model.CarrierWithPocketAndItemsEntity
import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import io.reactivex.Completable

interface CarrierRemoteDataSource {
    fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItemsEntity>): Completable
    fun importUserCarrierDbServer(): Completable
}