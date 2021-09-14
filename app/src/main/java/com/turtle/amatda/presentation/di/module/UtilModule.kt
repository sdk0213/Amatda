package com.turtle.amatda.presentation.di.module

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.turtle.amatda.presentation.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    // 위치 가져오기 관련 - FusedLocation
    @Singleton
    @Provides
    fun provideFusedLocation(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    // 위치 가져오기 관련 - LocationRequest
    @Singleton
    @Provides
    fun provideLocationRequest() : LocationRequest{
        return LocationRequest
            .create().apply {
                priority = LocationRequest.PRIORITY_LOW_POWER
                interval = 0
            }
    }
}