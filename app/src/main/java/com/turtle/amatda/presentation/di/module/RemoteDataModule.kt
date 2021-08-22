package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.api.WeatherAPIService
import com.turtle.amatda.data.db.dao.CarrierDao
import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.db.dao.PocketDao
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSource
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSourceImpl
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.item.ItemLocalDataSourceImpl
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSource
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSourceImpl
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