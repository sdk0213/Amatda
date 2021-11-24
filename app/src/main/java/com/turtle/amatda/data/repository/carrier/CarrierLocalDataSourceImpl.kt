package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.db.dao.CarrierDao
import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.db.dao.PocketDao
import com.turtle.amatda.data.model.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CarrierLocalDataSourceImpl @Inject constructor(
    private val carrierDao: CarrierDao,
    private val pocketDao: PocketDao,
    private val itemDao: ItemDao
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

    override fun getCarrierWithPocketAndItems(): Single<List<CarrierWithPocketAndItemsEntity>> {
        return carrierDao.getCarrierWithPocketAndItemsData()
    }

    override fun getObserveCarrierWithPocketAndItems(): Flowable<List<CarrierWithPocketAndItemsEntity>> {
        return carrierDao.getObserveCarrierWithPocketAndItemsData()
    }

    override fun initCarrierDB(): Completable {
        return carrierDao.initCarrierDB()
    }

    override fun insertCarrierDB(carrierList: List<CarrierWithPocketAndItemsEntity>): Completable {
        val carrierEntityList = mutableListOf<CarrierEntity>()
        val pocketEntityList = mutableListOf<PocketEntity>()
        val itemEntityList = mutableListOf<ItemEntity>()
        carrierList.forEach { CPI ->
            carrierEntityList.add(CPI.carrier)
            CPI.pockets.forEach { PI ->
                pocketEntityList.add(PI.pocket)
                itemEntityList.addAll(PI.items)
            }
        }

        return carrierDao.insertAll(carrierEntityList)
            .andThen(pocketDao.insertAll(pocketEntityList))
            .andThen(itemDao.insertAll(itemEntityList))
    }
}