package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.todo.ItemEntity
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.data.repository.item.ItemDataSourceFactory
import com.turtle.amatda.data.repository.item.ItemRepositoryImpl
import com.turtle.amatda.data.repository.todo.TodoDataSourceFactory
import com.turtle.amatda.data.repository.todo.TodoRepositoryImpl
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoRepositoryImpl(
        mapper: Mapper<TodoEntity, Todo>,
        factory: TodoDataSourceFactory
    ) : TodoRepository {
        return TodoRepositoryImpl(mapper, factory)
    }

    @Provides
    @Singleton
    fun provideItemRepositoryImpl(
        mapper: Mapper<ItemEntity, Item>,
        factory: ItemDataSourceFactory
    ) : ItemRepository {
        return ItemRepositoryImpl(mapper, factory)
    }

}














