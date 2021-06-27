package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.data.repository.carrier.CarrierDataSourceFactory
import com.turtle.amatda.data.repository.carrier.CarrierRepositoryImpl
import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemRepositoryImpl
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoRepositoryImpl(
        carrierMapper: Mapper<CarrierEntity, Carrier>,
        itemMapper: Mapper<ItemEntity, Item>,
        factory: CarrierDataSourceFactory
    ) : CarrierRepository {
        return CarrierRepositoryImpl(carrierMapper, itemMapper, factory)
    }

    @Provides
    @Singleton
    fun provideItemRepositoryImpl(
        mapper: Mapper<ItemEntity, Item>,
        factory: ItemDataSourceFactory
    ) : ItemRepository {
        return ItemRepositoryImpl(mapper, factory)
    }

}














