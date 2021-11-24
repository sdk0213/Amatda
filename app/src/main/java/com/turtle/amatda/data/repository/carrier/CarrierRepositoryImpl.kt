package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.*
import com.turtle.amatda.domain.model.*
import com.turtle.amatda.domain.repository.CarrierRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CarrierRepositoryImpl @Inject constructor(
    private val carrierMapper: Mapper<CarrierEntity, Carrier>,
    private val pocketMapper: Mapper<PocketEntity, Pocket>,
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

    override fun getCarrierWithPocketAndItems(): Single<List<CarrierWithPocketAndItems>> {
        return factory.getCarrierWithPocketAndItems().map { list ->
            list.map { carrierWithPocketAndItemsEntity ->
                CarrierWithPocketAndItems(
                    carrier = carrierMapper.entityToMap(carrierWithPocketAndItemsEntity.carrier),
                    pocketAndItems = carrierWithPocketAndItemsEntity.pockets.map { pocketAndItems ->
                        PocketAndItem(
                            pocket = pocketMapper.entityToMap(pocketAndItems.pocket),
                            items = pocketAndItems.items.map { itemEntity ->
                                itemMapper.entityToMap(itemEntity)
                            }
                        )
                    }
                )
            }
        }
    }

    override fun getObserveCarrierWithPocketAndItems(): Flowable<List<CarrierWithPocketAndItems>> {
        return factory.getObserveCarrierWithPocketAndItems().map { list ->
            list.map { carrierWithPocketAndItemsEntity ->
                CarrierWithPocketAndItems(
                    carrier = carrierMapper.entityToMap(carrierWithPocketAndItemsEntity.carrier),
                    pocketAndItems = carrierWithPocketAndItemsEntity.pockets.map { pocketAndItems ->
                        PocketAndItem(
                            pocket = pocketMapper.entityToMap(pocketAndItems.pocket),
                            items = pocketAndItems.items.map { itemEntity ->
                                itemMapper.entityToMap(itemEntity)
                            }
                        )
                    }
                )
            }
        }
    }

    override fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItems>): Completable {
        return factory.exportUserCarrierDbServer(
            userCarrier.map { carrier ->
                CarrierWithPocketAndItemsEntity(
                    carrier = carrierMapper.mapToEntity(carrier.carrier),
                    pockets = carrier.pocketAndItems.map { pocketAndItem ->
                        PocketAndItemsEntity(
                            pocket = pocketMapper.mapToEntity(pocketAndItem.pocket),
                            items = pocketAndItem.items.map { item ->
                                itemMapper.mapToEntity(item)
                            }
                        )
                    }
                )
            }
        )
    }

    override fun importUserCarrierDbServer(): Single<List<CarrierWithPocketAndItems>> {
        return factory.importUserCarrierDbServer().map { list ->
            list.map { carrierWithPocketAndItemsEntity ->
                CarrierWithPocketAndItems(
                    carrier = carrierMapper.entityToMap(carrierWithPocketAndItemsEntity.carrier),
                    pocketAndItems = carrierWithPocketAndItemsEntity.pockets.map { pocketAndItems ->
                        PocketAndItem(
                            pocket = pocketMapper.entityToMap(pocketAndItems.pocket),
                            items = pocketAndItems.items.map { itemEntity ->
                                itemMapper.entityToMap(itemEntity)
                            }
                        )
                    }
                )
            }
        }
    }

    override fun initCarrierDB(): Completable {
        return factory.initCarrierDB()
    }

    override fun insertCarrierDB(carrierList: List<CarrierWithPocketAndItems>): Completable {
        return factory.insertCarrierDB(
            carrierList.map { carrier ->
                CarrierWithPocketAndItemsEntity(
                    carrier = carrierMapper.mapToEntity(carrier.carrier),
                    pockets = carrier.pocketAndItems.map { pocketAndItem ->
                        PocketAndItemsEntity(
                            pocket = pocketMapper.mapToEntity(pocketAndItem.pocket),
                            items = pocketAndItem.items.map { item ->
                                itemMapper.mapToEntity(item)
                            }
                        )
                    }
                )
            }
        )
    }

}