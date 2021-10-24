package com.turtle.amatda.presentation.android

import androidx.work.Configuration
import com.google.android.gms.location.GeofencingClient
import com.turtle.amatda.presentation.android.di.DaggerAppComponent
import com.turtle.amatda.presentation.android.notification.NotificationUtil
import com.turtle.amatda.presentation.android.workers.WorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication(), Configuration.Provider {

    @Inject
    lateinit var geofencingClient: GeofencingClient

    @Inject
    lateinit var workerFactory: WorkerFactory

    @Inject
    lateinit var notificationUtil: NotificationUtil

    override fun onCreate() {
        super.onCreate()
        notificationUtil.buildNotificationChannel()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }
}