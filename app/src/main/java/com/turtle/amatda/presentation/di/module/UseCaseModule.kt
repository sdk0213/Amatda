package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.repository.TodoRepository
import com.turtle.amatda.domain.usecases.AddTodoUseCase
import com.turtle.amatda.domain.usecases.GetTodoUseCase
import com.turtle.amatda.domain.usecases.InsertItemFromAssetsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAddTodoUseCase(repository: TodoRepository): AddTodoUseCase {
        return AddTodoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTodoUseCase(repository: TodoRepository): GetTodoUseCase {
        return GetTodoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertItemUseCase(repository: ItemRepository): InsertItemFromAssetsUseCase {
        return InsertItemFromAssetsUseCase(repository)
    }

}