package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.*
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.data.model.PocketEntity
import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.model.Weather
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideItemMapper(): Mapper<ItemEntity, Item>{
        return ItemMapper()
    }

    @Provides
    fun provideCarrierMapper(): Mapper<CarrierEntity, Carrier>{
        return CarrierMapper()
    }

    @Provides
    fun providePocketMapper(): Mapper<PocketEntity, Pocket> {
        return PocketMapper()
    }

    @Provides
    fun provideWeatherResponseMapper(): ResponseMapper<WeatherResponse, List<Weather>> {
        return WeatherResponseMapper()
    }
}