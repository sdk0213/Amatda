package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.data.mapper.*
import com.turtle.amatda.data.model.*
import com.turtle.amatda.domain.model.*
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideItemMapper(): Mapper<ItemEntity, Item> {
        return ItemMapper()
    }

    @Provides
    fun provideCarrierMapper(): Mapper<CarrierEntity, Carrier> {
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

    @Provides
    fun provideTourResponseMapper(): ResponseMapper<TourXml, List<Tour>> {
        return TourResponseMapper()
    }

    @Provides
    fun provideTripMapper(): Mapper<TripAndTripZoneEntity, Trip> {
        return TripMapper()
    }

    @Provides
    fun provideTripZoneMapper(): Mapper<TripZoneEntity, TripZone> {
        return TripZoneMapper()
    }

    @Provides
    fun provideUserMapper(): Mapper<UserEntity, User> {
        return UserMapper()
    }
}