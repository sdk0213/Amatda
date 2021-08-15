package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.model.CarrierAndPocketEntity
import com.turtle.amatda.data.model.CarrierEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarrierDataSourceFactory @Inject constructor(
    private val localDataSource: CarrierLocalDataSource
) {
    fun getCarrierAll() : Flowable<List<CarrierEntity>> {
        return localDataSource.getCarrierAll()
    }

    fun insertCarrier(carrierEntity: CarrierEntity) : Completable {
        return localDataSource.insertCarrier(carrierEntity)
    }

    fun getCarrierAndPocket() : Flowable<List<CarrierAndPocketEntity>> {
        return localDataSource.getCarrierAndPocket()
    }

    fun deleteCarrier(carrierEntity: CarrierEntity) : Completable {
        return localDataSource.deleteCarrier(carrierEntity)
    }

    fun updateCarrier(carrierEntity: CarrierEntity) : Completable {
        return localDataSource.updateCarrier(carrierEntity)
    }
}