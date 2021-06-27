package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.db.dao.CarrierDao
import com.turtle.amatda.data.model.CarrierAndItemsEntity
import com.turtle.amatda.data.model.CarrierEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarrierLocalDataSourceImpl @Inject constructor(private val carrierDao: CarrierDao) : CarrierLocalDataSource{

    override fun getCarrierAll(): Flowable<List<CarrierEntity>> {
        return carrierDao.getAll()
    }

    override fun insertCarrier(carrier: CarrierEntity): Completable {
        return carrierDao.insert(carrier)
    }

    override fun getCarrierAndItems(): Flowable<List<CarrierAndItemsEntity>> {
        return carrierDao.getCarrierAndItemsData()
    }


}