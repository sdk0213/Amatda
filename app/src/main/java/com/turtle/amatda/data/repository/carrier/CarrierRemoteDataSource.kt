package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.model.CarrierWithPocketAndItemsEntity
import io.reactivex.Completable
import io.reactivex.Single

interface CarrierRemoteDataSource {
    fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItemsEntity>): Completable
    fun importUserCarrierDbServer(): Single<List<CarrierWithPocketAndItemsEntity>>
}