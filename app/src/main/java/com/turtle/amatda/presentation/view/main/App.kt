package com.turtle.amatda.presentation.view.main

import androidx.work.Configuration
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.turtle.amatda.presentation.di.DaggerAppComponent
import com.turtle.amatda.presentation.workers.WorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication(), Configuration.Provider {

    @Inject
    lateinit var geofencingClient: GeofencingClient

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }
}