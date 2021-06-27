package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndItems
import io.reactivex.Completable
import io.reactivex.Flowable

interface CarrierRepository {
    fun getUserCarrierAll() : Flowable<List<Carrier>>
    fun insertCarrier(carrier: Carrier) : Completable
    fun getCarrierAndItems() : Flowable<List<CarrierAndItems>>
}