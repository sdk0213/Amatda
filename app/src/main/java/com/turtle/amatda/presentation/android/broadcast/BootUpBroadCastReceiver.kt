package com.turtle.amatda.presentation.android.broadcast

import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.turtle.amatda.presentation.android.workmanager.ManageTripZoneGeofenceWorker
import dagger.android.DaggerBroadcastReceiver
import timber.log.Timber
import javax.inject.Inject

class BootUpBroadCastReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.let {
            if (it.action == Intent.ACTION_BOOT_COMPLETED) {
                workManager.enqueue(
                    OneTimeWorkRequestBuilder<ManageTripZoneGeofenceWorker>().build()
                )
            }
        }?.run {
            Timber.e("BootUpReceiver : intent is null")
        }
    }

}