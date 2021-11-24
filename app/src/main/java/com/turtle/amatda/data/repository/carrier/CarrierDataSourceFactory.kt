package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.model.CarrierAndPocketEntity
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.CarrierWithPocketAndItemsEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CarrierDataSourceFactory @Inject constructor(
    private val localDataSource: CarrierLocalDataSource,
    private val remoteDataSource: CarrierRemoteDataSource
) {
    fun getCarrierAll(): Flowable<List<CarrierEntity>> {
        return localDataSource.getCarrierAll()
    }

    fun insertCarrier(carrierEntity: CarrierEntity): Completable {
        return localDataSource.insertCarrier(carrierEntity)
    }

    fun getCarrierAndPocket(): Flowable<List<CarrierAndPocketEntity>> {
        return localDataSource.getCarrierAndPocket()
    }

    fun deleteCarrier(carrierEntity: CarrierEntity): Completable {
        return localDataSource.deleteCarrier(carrierEntity)
    }

    fun updateCarrier(carrierEntity: CarrierEntity): Completable {
        return localDataSource.updateCarrier(carrierEntity)
    }

    fun getCarrierWithPocketAndItems(): Single<List<CarrierWithPocketAndItemsEntity>> {
        return localDataSource.getCarrierWithPocketAndItems()
    }

    fun getObserveCarrierWithPocketAndItems(): Flowable<List<CarrierWithPocketAndItemsEntity>> {
        return localDataSource.getObserveCarrierWithPocketAndItems()
    }

    fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItemsEntity>): Completable {
        return remoteDataSource.exportUserCarrierDbServer(userCarrier)
    }

    fun importUserCarrierDbServer(): Single<List<CarrierWithPocketAndItemsEntity>> {
        return remoteDataSource.importUserCarrierDbServer()
    }

    fun initCarrierDB(): Completable {
        return localDataSource.initCarrierDB()
    }

    fun insertCarrierDB(carrierList: List<CarrierWithPocketAndItemsEntity>): Completable {
        return localDataSource.insertCarrierDB(carrierList)
    }
}