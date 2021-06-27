package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.db.dao.CarrierDao
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.item.ItemLocalDataSourceImpl
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSource
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideTodoLocalDataSourceImpl(todoDao: CarrierDao): CarrierLocalDataSource{
        return CarrierLocalDataSourceImpl(todoDao)
    }

    @Provides
    @Singleton
    fun provideItemLocalDataSourceImpl(itemDao: ItemDao): ItemLocalDataSource{
        return ItemLocalDataSourceImpl(itemDao)
    }

}