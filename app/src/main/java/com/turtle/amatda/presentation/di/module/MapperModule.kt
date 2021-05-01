package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.ItemMapper
import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.mapper.TodoMapper
import com.turtle.amatda.data.model.todo.ItemEntity
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Todo
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideItemMapper(): Mapper<ItemEntity, Item>{
        return ItemMapper()
    }

    @Provides
    fun provideTodoMapper(): Mapper<TodoEntity, Todo>{
        return TodoMapper()
    }
}