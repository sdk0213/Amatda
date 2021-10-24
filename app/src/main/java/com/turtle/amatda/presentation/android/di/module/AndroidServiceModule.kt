package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.presentation.android.di.scope.ServiceScope
import com.turtle.amatda.presentation.android.service.GeofenceReceiverService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidServiceModule {

    @ServiceScope
    @ContributesAndroidInjector(modules = [ServiceModule::class])
    abstract fun getGeofenceReceiverService() : GeofenceReceiverService

}