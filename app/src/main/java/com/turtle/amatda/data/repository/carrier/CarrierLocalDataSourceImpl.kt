package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.db.dao.CarrierDao
import com.turtle.amatda.data.model.CarrierAndPocketEntity
import com.turtle.amatda.data.model.CarrierEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarrierLocalDataSourceImpl @Inject constructor(
    private val carrierDao: CarrierDao
) : CarrierLocalDataSource {

    override fun getCarrierAll(): Flowable<List<CarrierEntity>> {
        return carrierDao.getAll()
    }

    override fun insertCarrier(carrierEntity: CarrierEntity): Completable {
        return carrierDao.insert(carrierEntity)
    }

    override fun getCarrierAndPocket(): Flowable<List<CarrierAndPocketEntity>> {
        return carrierDao.getCarrierAndPocketData()
    }

    override fun deleteCarrier(carrierEntity: CarrierEntity): Completable {
        return carrierDao.delete(carrierEntity)
    }

    override fun updateCarrier(carrierEntity: CarrierEntity): Completable {
        return carrierDao.update(carrierEntity)
    }

}