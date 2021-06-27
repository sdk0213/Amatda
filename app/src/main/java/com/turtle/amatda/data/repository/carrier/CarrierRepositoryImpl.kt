package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndItems
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.CarrierRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarrierRepositoryImpl @Inject constructor(
    private val carrierMapper: Mapper<CarrierEntity, Carrier>,
    private val itemMapper: Mapper<ItemEntity, Item>,
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

    override fun getCarrierAndItems(): Flowable<List<CarrierAndItems>> {
        return factory.getCarrierAndItems().map { list ->
            list.map { carrierAndItemsEntity ->
                CarrierAndItems(
                    carrierMapper.entityToMap(carrierAndItemsEntity.carrier),
                    carrierAndItemsEntity.items.map { itemEntity ->
                        itemMapper.entityToMap(itemEntity)
                    }
                )
            }
        }
    }
}