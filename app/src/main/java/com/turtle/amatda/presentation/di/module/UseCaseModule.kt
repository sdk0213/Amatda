package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.repository.CarrierRepository
import com.turtle.amatda.domain.usecases.AddUserCarrierUseCase
import com.turtle.amatda.domain.usecases.GetUserCarrierUseCase
import com.turtle.amatda.domain.usecases.InsertItemFromAssetsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAddCarrierUseCase(repository: CarrierRepository): AddUserCarrierUseCase {
        return AddUserCarrierUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCarrierUseCase(repository: CarrierRepository): GetUserCarrierUseCase {
        return GetUserCarrierUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertItemUseCase(repository: ItemRepository): InsertItemFromAssetsUseCase {
        return InsertItemFromAssetsUseCase(repository)
    }

}