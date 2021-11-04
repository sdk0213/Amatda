package com.turtle.amatda.presentation.android

import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.location.GeofencingClient
import com.turtle.amatda.BuildConfig
import com.turtle.amatda.presentation.android.di.DaggerAppComponent
import com.turtle.amatda.presentation.android.di.factory.WorkerFactory
import com.turtle.amatda.presentation.android.notification.NotificationUtil
import com.turtle.amatda.presentation.android.workmanager.ManageTripZoneGeofenceWorker
import com.turtle.amatda.presentation.utilities.CatchUnexpectedException
import com.turtle.amatda.presentation.utilities.CustomTimberDebug
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication(), Configuration.Provider {

    @Inject
    lateinit var geofencingClient: GeofencingClient

    @Inject
    lateinit var workerFactory: WorkerFactory

    @Inject
    lateinit var notificationUtil: NotificationUtil

    @Inject
    lateinit var customTimberDebug: CustomTimberDebug

    @Inject
    lateinit var catchUnexpectedException: CatchUnexpectedException

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        notificationUtil.buildNotificationChannel()

        // Timber 로그 라이브러리 초기화
        if (BuildConfig.DEBUG) {
            Timber.plant(customTimberDebug)
            Timber.d("onCreate")
        }

        Thread.setDefaultUncaughtExceptionHandler(catchUnexpectedException)

        init()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    private fun init() {
        workManager.enqueue(
            OneTimeWorkRequestBuilder<ManageTripZoneGeofenceWorker>().build()
        )
    }
}