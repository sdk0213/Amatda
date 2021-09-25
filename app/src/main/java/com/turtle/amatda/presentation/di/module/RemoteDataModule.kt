package com.turtle.amatda.presentation.di.module

import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.turtle.amatda.data.api.TourAPIService
import com.turtle.amatda.data.api.WeatherAPIService
import com.turtle.amatda.data.repository.area.AreaRemoteDataSource
import com.turtle.amatda.data.repository.location.LocationRemoteDataSource
import com.turtle.amatda.data.repository.weather.WeatherRemoteDataSource
import com.turtle.amatda.data.repository.weather.WeatherRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideWeatherRemoteDataSourceImpl(
        api: WeatherAPIService
    ): WeatherRemoteDataSource {
        return WeatherRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideAreaRemoteDataSource(
        api: TourAPIService
    ): AreaRemoteDataSource {
        return AreaRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideLocationRemoteDataSource(
        fusedLocationProviderClient: FusedLocationProviderClient,
        locationRequest: LocationRequest,
        geocoder: Geocoder
    ): LocationRemoteDataSource {
        return LocationRemoteDataSource(fusedLocationProviderClient, locationRequest, geocoder)
    }

}