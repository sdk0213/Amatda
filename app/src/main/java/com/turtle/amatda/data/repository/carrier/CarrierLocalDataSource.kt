package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.model.CarrierAndPocketEntity
import com.turtle.amatda.data.model.CarrierEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface CarrierLocalDataSource {

    fun getCarrierAll(): Flowable<List<CarrierEntity>>
    fun insertCarrier(carrier: CarrierEntity): Completable
    fun getCarrierAndPocket() : Flowable<List<CarrierAndPocketEntity>>

}