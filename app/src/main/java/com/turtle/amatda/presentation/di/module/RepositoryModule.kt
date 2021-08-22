package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.mapper.ResponseMapper
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.data.model.PocketEntity
import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.data.repository.carrier.CarrierDataSourceFactory
import com.turtle.amatda.data.repository.carrier.CarrierRepositoryImpl
import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemRepositoryImpl
import com.turtle.amatda.data.repository.pocket.PocketDataSourceFactory
import com.turtle.amatda.data.repository.pocket.PocketRepositoryImpl
import com.turtle.amatda.data.repository.weather.WeatherDataSourceFactory
import com.turtle.amatda.data.repository.weather.WeatherRepositoryImpl
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.repository.PocketRepository
import com.turtle.amatda.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCarrierRepositoryImpl(
        carrierMapper: Mapper<CarrierEntity, Carrier>,
        pocketMapper: Mapper<PocketEntity, Pocket>,
        factory: CarrierDataSourceFactory
    ): CarrierRepository {
        return CarrierRepositoryImpl(carrierMapper, pocketMapper, factory)
    }

    @Provides
    @Singleton
    fun provideItemRepositoryImpl(
        mapper: Mapper<ItemEntity, Item>,
        factory: ItemDataSourceFactory
    ): ItemRepository {
        return ItemRepositoryImpl(mapper, factory)
    }

    @Provides
    @Singleton
    fun providePocketRepositoryImpl(
        pocketMapper: Mapper<PocketEntity, Pocket>,
        itemMapper: Mapper<ItemEntity, Item>,
        factory: PocketDataSourceFactory
    ): PocketRepository {
        return PocketRepositoryImpl(pocketMapper, itemMapper, factory)
    }

    @Provides
    @Singleton
    fun provideWeatherRepositoryImpl(
        mapper: ResponseMapper<WeatherResponse>,
        factory: WeatherDataSourceFactory
    ): WeatherRepository {
        return WeatherRepositoryImpl(mapper, factory)
    }

}