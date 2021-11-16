package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndPocket
import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface CarrierRepository {
    fun getUserCarrierAll(): Flowable<List<Carrier>>
    fun insertCarrier(carrier: Carrier): Completable
    fun getCarrierAndPocket(): Flowable<List<CarrierAndPocket>>
    fun deleteCarrier(carrier: Carrier): Completable
    fun updateCarrier(carrier: Carrier): Completable
    fun getCarrierWithPocketAndItems(): Single<List<CarrierWithPocketAndItems>>
    fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItems>): Completable
    fun importUserCarrierDbServer(): Single<List<CarrierWithPocketAndItems>>
    fun initCarrierDB(): Completable
    fun insertCarrierDB(carrierList: List<CarrierWithPocketAndItems>): Completable
}