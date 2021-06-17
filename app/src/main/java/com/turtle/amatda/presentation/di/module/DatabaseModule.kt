package com.turtle.amatda.presentation.di.module

import android.content.Context
import com.turtle.amatda.data.db.AppDatabase
import com.turtle.amatda.data.db.PreferenceManager
import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.db.dao.ToDoDao
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
    fun provideTodoDao(appDatabase: AppDatabase): ToDoDao {
        return appDatabase.todoDao()
    }

    @Provides
    fun provideItemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }


}