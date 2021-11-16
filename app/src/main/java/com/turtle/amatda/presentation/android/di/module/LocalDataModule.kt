package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.data.db.dao.*
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSource
import com.turtle.amatda.data.repository.carrier.CarrierLocalDataSourceImpl
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.item.ItemLocalDataSourceImpl
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSource
import com.turtle.amatda.data.repository.pocket.PocketLocalDataSourceImpl
import com.turtle.amatda.data.repository.trip.TripLocalDataSource
import com.turtle.amatda.data.repository.trip.TripLocalDataSourceImpl
import com.turtle.amatda.data.repository.tripZone.TripZoneLocalDataSource
import com.turtle.amatda.data.repository.tripZone.TripZoneLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideCarrierLocalDataSourceImpl(
        carrierDao: CarrierDao,
        pocketDao: PocketDao,
        itemDao: ItemDao
    ): CarrierLocalDataSource {
        return CarrierLocalDataSourceImpl(
            carrierDao,
            pocketDao,
            itemDao
        )
    }

    @Provides
    @Singleton
    fun provideItemLocalDataSourceImpl(itemDao: ItemDao): ItemLocalDataSource {
        return ItemLocalDataSourceImpl(itemDao)
    }

    @Provides
    @Singleton
    fun providePocketLocalDataSourceImpl(pocketDao: PocketDao): PocketLocalDataSource {
        return PocketLocalDataSourceImpl(pocketDao)
    }

    @Provides
    @Singleton
    fun provideTripLocalDataSourceImpl(tripDao: TripDao): TripLocalDataSource {
        return TripLocalDataSourceImpl(tripDao)
    }

    @Provides
    @Singleton
    fun provideTripZoneLocalDataSourceImpl(tripZoneDao: TripZoneDao): TripZoneLocalDataSource {
        return TripZoneLocalDataSourceImpl(tripZoneDao)
    }
}