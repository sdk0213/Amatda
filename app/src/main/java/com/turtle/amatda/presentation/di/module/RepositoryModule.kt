package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.mapper.ResponseMapper
import com.turtle.amatda.data.model.*
import com.turtle.amatda.data.repository.area.AreaRemoteDataSource
import com.turtle.amatda.data.repository.area.AreaRepositoryImpl
import com.turtle.amatda.data.repository.carrier.CarrierDataSourceFactory
import com.turtle.amatda.data.repository.carrier.CarrierRepositoryImpl
import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemRepositoryImpl
import com.turtle.amatda.data.repository.location.LocationRemoteDataSource
import com.turtle.amatda.data.repository.location.LocationRepositoryImpl
import com.turtle.amatda.data.repository.pocket.PocketDataSourceFactory
import com.turtle.amatda.data.repository.pocket.PocketRepositoryImpl
import com.turtle.amatda.data.repository.tour.TourRemoteDataSource
import com.turtle.amatda.data.repository.tour.TourRepositoryImpl
import com.turtle.amatda.data.repository.trip.TripDataSourceFactory
import com.turtle.amatda.data.repository.trip.TripRepositoryImpl
import com.turtle.amatda.data.repository.weather.WeatherDataSourceFactory
import com.turtle.amatda.data.repository.weather.WeatherRepositoryImpl
import com.turtle.amatda.domain.model.*
import com.turtle.amatda.domain.repository.*
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
        mapper: ResponseMapper<WeatherJson, List<Weather>>,
        factory: WeatherDataSourceFactory
    ): WeatherRepository {
        return WeatherRepositoryImpl(mapper, factory)
    }

    @Provides
    @Singleton
    fun provideLocationRepositoryImpl(
        locationRemoteDataSource: LocationRemoteDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(locationRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAreaRepositoryImpl(
        mapper: ResponseMapper<AreaXml, List<Area>>,
        areaRemoteDataSource: AreaRemoteDataSource
    ): AreaRepository {
        return AreaRepositoryImpl(mapper, areaRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideTourRepositoryImpl(
        mapper: ResponseMapper<TourXml, List<Tour>>,
        tourRemoteDataSource: TourRemoteDataSource
    ): TourRepository {
        return TourRepositoryImpl(mapper, tourRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideTripRepositoryImpl(
        tripMapper: Mapper<TripAndTripZoneEntity, Trip>,
        factory: TripDataSourceFactory
    ): TripRepository {
        return TripRepositoryImpl(tripMapper, factory)
    }

}