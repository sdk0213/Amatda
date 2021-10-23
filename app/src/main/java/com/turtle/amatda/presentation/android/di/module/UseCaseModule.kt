package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.domain.repository.*
import com.turtle.amatda.domain.usecases.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAddUserCarrierUseCase(repository: CarrierRepository): AddUserCarrierUseCase {
        return AddUserCarrierUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteCarrierUseCase(repository: CarrierRepository): DeleteCarrierUseCase {
        return DeleteCarrierUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteItemUseCase(repository: ItemRepository): DeleteItemUseCase {
        return DeleteItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllItemUseCase(repository: ItemRepository): GetAllItemUseCase {
        return GetAllItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPocketAndItemUseCase(repository: PocketRepository): GetAllPocketAndItemUseCase {
        return GetAllPocketAndItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPocketUseCase(repository: CarrierRepository): GetPocketUseCase {
        return GetPocketUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertItemFromAssetsUseCase(repository: ItemRepository): InsertItemFromAssetsUseCase {
        return InsertItemFromAssetsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveCarrierItemsUseCase(repository: ItemRepository): SaveCarrierItemsUseCase {
        return SaveCarrierItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveItemUseCase(repository: ItemRepository): SaveItemUseCase {
        return SaveItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavePocketUseCase(repository: PocketRepository): SavePocketUseCase {
        return SavePocketUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCarrierUseCase(repository: CarrierRepository): UpdateCarrierUseCase {
        return UpdateCarrierUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateItemUseCase(repository: ItemRepository): UpdateItemUseCase {
        return UpdateItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdatePocketUseCase(repository: PocketRepository): UpdatePocketUseCase {
        return UpdatePocketUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLocationUseCase(repository: LocationRepository): GetLocationUseCase {
        return GetLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAreaUseCase(repository: AreaRepository): GetAreaUseCase {
        return GetAreaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTourUseCase(repository: TourRepository): GetTourUseCase {
        return GetTourUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTripUseCase(repository: TripRepository): AddTripUseCase {
        return AddTripUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllTripUseCase(repository: TripRepository): GetAllTripUseCase {
        return GetAllTripUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveTripUseCase(repository: TripRepository): SaveTripUseCase {
        return SaveTripUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTripUseCase(repository: TripRepository): GetTripUseCase {
        return GetTripUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTripZoneUseCase(repository: TripZoneRepository): DeleteTripZoneUseCase {
        return DeleteTripZoneUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllTripZone(repository: TripZoneRepository): GetAllTripZoneUseCase {
        return GetAllTripZoneUseCase(repository)
    }
}