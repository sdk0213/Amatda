package com.turtle.amatda.presentation.android.di.module

import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.firebase.auth.FirebaseAuth
import com.turtle.amatda.data.api.*
import com.turtle.amatda.data.repository.area.AreaRemoteDataSource
import com.turtle.amatda.data.repository.carrier.CarrierRemoteDataSource
import com.turtle.amatda.data.repository.carrier.CarrierRemoteDataSourceImpl
import com.turtle.amatda.data.repository.location.LocationRemoteDataSource
import com.turtle.amatda.data.repository.tour.TourRemoteDataSource
import com.turtle.amatda.data.repository.user.UserRemoteDataSource
import com.turtle.amatda.data.repository.user.UserRemoteDataSourceImpl
import com.turtle.amatda.data.repository.user_auth.UserAuthRemoteDataSource
import com.turtle.amatda.data.repository.user_auth.UserAuthRemoteDataSourceImpl
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

    @Provides
    @Singleton
    fun provideTourRemoteDataSource(
        api: TourAPIService
    ): TourRemoteDataSource {
        return TourRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideUserAuthRemoteDataSource(
        api: FirebaseAuthApiService
    ): UserAuthRemoteDataSource {
        return UserAuthRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        storeApi: FirebaseFirestoreApiService,
        storageApi: FirebaseStorageApiService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(storeApi, storageApi)
    }

    @Provides
    @Singleton
    fun provideCarrierRemoteDataSource(
        storeApi: FirebaseFirestoreApiService,
        firebaseAuth: FirebaseAuth
    ): CarrierRemoteDataSource {
        return CarrierRemoteDataSourceImpl(storeApi, firebaseAuth)
    }
}