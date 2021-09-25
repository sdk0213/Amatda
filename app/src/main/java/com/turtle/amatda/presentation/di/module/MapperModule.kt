package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.*
import com.turtle.amatda.data.model.*
import com.turtle.amatda.domain.model.*
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
    fun provideWeatherResponseMapper(): ResponseMapper<WeatherJson, List<Weather>> {
        return WeatherResponseMapper()
    }

    @Provides
    fun provideAreaResponseMapper(): ResponseMapper<AreaXml, List<Area>> {
        return AreaResponseMapper()
    }
}