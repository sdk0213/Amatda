package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.data.repository.carrier.CarrierDataSourceFactory
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSource
import com.turtle.amatda.data.repository.fireBase.FirebaseDataSourceFactory
import com.turtle.amatda.data.repository.fireBase.FirebaseRemoteDataSource
import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.pocket.PocketDataSourceFactory
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSource
import com.turtle.amatda.data.repository.trip.TripDataSourceFactory
import com.turtle.amatda.data.repository.trip.TripLocalDataSource
import com.turtle.amatda.data.repository.tripZone.TripZoneDataSourceFactory
import com.turtle.amatda.data.repository.tripZone.TripZoneLocalDataSource
import com.turtle.amatda.data.repository.weather.WeatherDataSourceFactory
import com.turtle.amatda.data.repository.weather.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceFactoryModule {

    @Provides
    @Singleton
    fun provideCarrierDataSourceFactory(localDataSource: CarrierLocalDataSource): CarrierDataSourceFactory {
        return CarrierDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideItemDataSourceFactory(localDataSource: ItemLocalDataSource): ItemDataSourceFactory {
        return ItemDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun providePocketDataSourceFactory(localDataSource: PocketLocalDataSource): PocketDataSourceFactory {
        return PocketDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideWeatherDataSourceFactory(localDataSource: WeatherRemoteDataSource): WeatherDataSourceFactory {
        return WeatherDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideTripDataSourceFactory(localDataSource: TripLocalDataSource): TripDataSourceFactory {
        return TripDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideTripZoneDataSourceFactory(localDataSource: TripZoneLocalDataSource): TripZoneDataSourceFactory {
        return TripZoneDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseDataSourceFactory(localDataSource: FirebaseRemoteDataSource): FirebaseDataSourceFactory {
        return FirebaseDataSourceFactory(localDataSource)
    }
}