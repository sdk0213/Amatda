package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.db.dao.CarrierDao
import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.db.dao.PocketDao
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSource
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSourceImpl
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.item.ItemLocalDataSourceImpl
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSource
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideCarrierLocalDataSourceImpl(carrierDao: CarrierDao): CarrierLocalDataSource{
        return CarrierLocalDataSourceImpl(carrierDao)
    }

    @Provides
    @Singleton
    fun provideItemLocalDataSourceImpl(itemDao: ItemDao): ItemLocalDataSource{
        return ItemLocalDataSourceImpl(itemDao)
    }

    @Provides
    @Singleton
    fun providePocketLocalDataSourceImpl(pocketDao: PocketDao): PocketLocalDataSource {
        return PocketLocalDataSourceImpl(pocketDao)
    }


}