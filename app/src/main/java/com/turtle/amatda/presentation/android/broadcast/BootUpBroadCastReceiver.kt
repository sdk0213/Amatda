package com.turtle.amatda.presentation.android.broadcast

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.turtle.amatda.presentation.android.workmanager.KeepServiceWorker
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class BootUpBroadCastReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.let {
            if (it.action == Intent.ACTION_BOOT_COMPLETED) {
                workManager.enqueue(
                    OneTimeWorkRequestBuilder<KeepServiceWorker>().build()
                )
            }
        }?.run {
            Log.e("sudeky", "BootUpReceiver : intent is null")
        }
    }

}