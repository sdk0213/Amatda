package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.api.WeatherAPIService
import com.turtle.amatda.data.repository.weather.WeatherRemoteDataSource
import com.turtle.amatda.data.repository.weather.WeatherRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideWeatherRemoteDataSourceImpl(api: WeatherAPIService): WeatherRemoteDataSource {
        return WeatherRemoteDataSourceImpl(api)
    }

}