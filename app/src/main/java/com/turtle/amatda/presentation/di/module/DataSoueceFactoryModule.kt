package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.carrier.CarrierDataSourceFactory
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSoueceFactoryModule {

    @Provides
    @Singleton
    fun provideTodoDataSourceFactory(localDataSource: CarrierLocalDataSource): CarrierDataSourceFactory {
        return CarrierDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideItemDataSourceFactory(localDataSource: ItemLocalDataSource): ItemDataSourceFactory {
        return ItemDataSourceFactory(localDataSource)
    }

}