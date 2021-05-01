package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.todo.TodoDataSourceFactory
import com.turtle.amatda.data.repository.todo.TodoLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSoueceFactoryModule {

    @Provides
    @Singleton
    fun provideTodoDataSourceFactory(localDataSource: TodoLocalDataSource): TodoDataSourceFactory {
        return TodoDataSourceFactory(localDataSource)
    }

    @Provides
    @Singleton
    fun provideItemDataSourceFactory(localDataSource: ItemLocalDataSource): ItemDataSourceFactory {
        return ItemDataSourceFactory(localDataSource)
    }

}