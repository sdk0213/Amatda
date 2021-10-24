package com.turtle.amatda.presentation.android.di.module

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.turtle.amatda.BuildConfig
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import com.turtle.amatda.presentation.android.service.GeofenceReceiverService
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UtilModule {

    // FusedLocation
    // 위치 가져오기 관련
    @Singleton
    @Provides
    fun provideFusedLocation(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    // LocationRequest
    // 위치 가져오기 관련
    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest
            .create().apply {
                priority = LocationRequest.PRIORITY_LOW_POWER
                interval = 0
            }
    }

    // Geocoder
    // 좌표 -> 주소
    @Singleton
    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Geocoder(context, context.resources.configuration.locales.get(0))
        } else {
            Geocoder(context, context.resources.configuration.locale)
        }
    }

    // Google Place
    // 구글 플레이스
    @Provides
    fun provideGooglePlaceClient(@ApplicationContext context: Context): PlacesClient {
        Places.initialize(context, BuildConfig.PLACES_API_KEY)
        return Places.createClient(context)
    }

    // 구글 지오펜싱
    @Provides
    @Singleton
    fun provideGoogleGeofencingClient(@ApplicationContext context: Context): GeofencingClient {
        return LocationServices.getGeofencingClient(context)
    }

    // 구글 지오펜싱 PendingIntent
    @Provides
    @Named("GeoPendingIntent")
    fun provideGoogleGeofencePendingIntent(
        @ApplicationContext context: Context,
        @Named("GeoIntent") intent: Intent
    ): PendingIntent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getService(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    // 구글 지오펜싱 Intent
    @Provides
    @Named("GeoIntent")
    fun provideGoogleGeoIntent(@ApplicationContext context: Context): Intent {
        return Intent(context, GeofenceReceiverService::class.java)
    }
}