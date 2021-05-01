package com.turtle.amatda.presentation.di.module

import android.content.Context
import com.turtle.amatda.data.db.AppDatabase
import com.turtle.amatda.data.db.item.ItemDao
import com.turtle.amatda.data.db.todo.ToDoDao
import com.turtle.amatda.presentation.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

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