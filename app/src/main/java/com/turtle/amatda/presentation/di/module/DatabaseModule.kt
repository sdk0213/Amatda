package com.turtle.amatda.presentation.di.module

import android.content.Context
import com.turtle.amatda.data.db.AppDatabase
import com.turtle.amatda.data.db.PreferenceManager
import com.turtle.amatda.data.db.dao.*
import com.turtle.amatda.presentation.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideTodoDao(appDatabase: AppDatabase): CarrierDao {
        return appDatabase.todoDao()
    }

    @Provides
    fun provideItemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }

    @Provides
    fun providePocketDao(appDatabase: AppDatabase): PocketDao {
        return appDatabase.pocketDao()
    }

    @Provides
    fun provideTripDao(appDatabase: AppDatabase): TripDao {
        return appDatabase.TripDao()
    }

    @Provides
    fun provideTripZoneDao(appDatabase: AppDatabase): TripZoneDao {
        return appDatabase.TripZoneDao()
    }

}