package com.turtle.amatda.presentation.android.workmanager

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.di.factory.ChildWorkerFactory
import com.turtle.amatda.presentation.android.service.GeofenceReceiverService
import com.turtle.amatda.presentation.utilities.thisServiceIsForeGroundService
import timber.log.Timber

class KeepServiceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val androidUtil: AndroidUtil
) : Worker(appContext, params) {

    override fun doWork(): Result {
        try {
            if (!androidUtil.isServiceRunning(GeofenceReceiverService::class.java)) {
                Timber.d("GeofenceReceiverService start")
                if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                    applicationContext.startForegroundService(
                        Intent(
                            applicationContext,
                            GeofenceReceiverService::class.java
                        ).apply {
                            putExtra(thisServiceIsForeGroundService, true)
                        }
                    )
                } else {
                    applicationContext.startService(
                        Intent(
                            applicationContext,
                            GeofenceReceiverService::class.java
                        )
                    )
                }
            } else {
                Timber.d("GeofenceReceiverService is already exist")
            }
            return Result.success()
        } catch (ex: Exception) {
            Timber.e("Error executing start GeofenceReceiverService exception is : $ex")
            return Result.failure()
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}