package com.turtle.amatda.presentation.android.di.module

import android.content.Context
import android.content.SharedPreferences
import com.turtle.amatda.data.db.AppDatabase
import com.turtle.amatda.presentation.android.shard_pref.SharedPrefUtil
import com.turtle.amatda.data.db.dao.*
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePreferenceManager(
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): SharedPrefUtil {
        return SharedPrefUtil(sharedPreferences, editor)
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