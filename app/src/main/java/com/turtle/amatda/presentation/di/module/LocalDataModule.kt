package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.db.dao.ItemDao
import com.turtle.amatda.data.db.dao.ToDoDao
import com.turtle.amatda.data.repository.item.ItemLocalDataSource
import com.turtle.amatda.data.repository.item.ItemLocalDataSourceImpl
import com.turtle.amatda.data.repository.todo.TodoLocalDataSource
import com.turtle.amatda.data.repository.todo.TodoLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideTodoLocalDataSourceImpl(todoDao: ToDoDao): TodoLocalDataSource{
        return TodoLocalDataSourceImpl(todoDao)
    }

    @Provides
    @Singleton
    fun provideItemLocalDataSourceImpl(itemDao: ItemDao): ItemLocalDataSource{
        return ItemLocalDataSourceImpl(itemDao)
    }

}