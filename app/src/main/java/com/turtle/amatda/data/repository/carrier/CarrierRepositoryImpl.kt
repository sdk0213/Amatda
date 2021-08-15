package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.PocketEntity
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndPocket
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.repository.CarrierRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarrierRepositoryImpl @Inject constructor(
    private val carrierMapper: Mapper<CarrierEntity, Carrier>,
    private val pocketMapper: Mapper<PocketEntity, Pocket>,
    private val factory: CarrierDataSourceFactory
) : CarrierRepository {

    override fun getUserCarrierAll(): Flowable<List<Carrier>> {
        return factory.getCarrierAll().map { list ->
            list.map { carrier ->
                carrierMapper.entityToMap(carrier)
            }
        }
    }

    override fun insertCarrier(carrier: Carrier): Completable {
        val carrierEntity = carrierMapper.mapToEntity(carrier)
        return factory.insertCarrier(carrierEntity)
    }

    override fun getCarrierAndPocket(): Flowable<List<CarrierAndPocket>> {
        return factory.getCarrierAndPocket().map { list ->
            list.map { carrierAndPocketEntity ->
                CarrierAndPocket(
                    carrierMapper.entityToMap(carrierAndPocketEntity.carrier),
                    carrierAndPocketEntity.pockets.map { pocketEntity ->
                        pocketMapper.entityToMap(pocketEntity)
                    }
                )
            }
        }
    }

    override fun deleteCarrier(carrier: Carrier): Completable {
        val carrierEntity = carrierMapper.mapToEntity(carrier)
        return factory.deleteCarrier(carrierEntity)
    }

    override fun updateCarrier(carrier: Carrier): Completable {
        val carrierEntity = carrierMapper.mapToEntity(carrier)
        return factory.updateCarrier(carrierEntity)
    }
}