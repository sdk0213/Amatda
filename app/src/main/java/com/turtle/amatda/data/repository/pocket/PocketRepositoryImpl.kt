package com.turtle.amatda.data.repository.pocket

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.data.model.PocketEntity
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.model.PocketAndItem
import com.turtle.amatda.domain.repository.PocketRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class PocketRepositoryImpl @Inject constructor(
    private val pocketMapper: Mapper<PocketEntity, Pocket>,
    private val itemMapper: Mapper<ItemEntity, Item>,
    private val factory: PocketDataSourceFactory
) : PocketRepository {

    override fun getUserPocketAll(): Flowable<List<Pocket>> {
        return factory.getPocketAll().map { list ->
            list.map { pocket ->
                pocketMapper.entityToMap(pocket)
            }
        }
    }

    override fun insertPocket(pocket: Pocket): Completable {
        val pocketEntity = pocketMapper.mapToEntity(pocket)
        return factory.insertPocket(pocketEntity)
    }

    override fun deletePocket(pocket: Pocket): Completable {
        val pocketEntity = pocketMapper.mapToEntity(pocket)
        return factory.deletePocket(pocketEntity)
    }

    override fun updatePocketName(pocket: Pocket): Completable {
        val pocketEntity = pocketMapper.mapToEntity(pocket)
        return factory.updatePocketName(pocketEntity)
    }

    override fun getPocketAndItem(carrierId: Long): Flowable<List<PocketAndItem>> {
        return factory.getPocketAndItems(carrierId).map { list ->
            list.map { pocketAndItemsEntity ->
                PocketAndItem(
                    pocketMapper.entityToMap(pocketAndItemsEntity.pocket),
                    pocketAndItemsEntity.items.map { itemEntity ->
                        itemMapper.entityToMap(itemEntity)
                    }
                )
            }
        }
    }
}