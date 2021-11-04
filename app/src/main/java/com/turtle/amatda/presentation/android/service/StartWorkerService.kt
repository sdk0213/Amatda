package com.turtle.amatda.presentation.android.service

import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.workmanager.ManageTripZoneGeofenceWorker
import timber.log.Timber
import javax.inject.Inject

// 알람예약시 PendingIntent 중에 PendingIntent.getWorker 와 같은 기능이 없기 때문에 서비스로 실행하여 진행
class StartWorkerService : BaseService() {

    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var androidUtil: AndroidUtil

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Timber.d("알람 매니저에 ManageTripZoneGeofenceWorker 가 작동하였습니다.")
        androidUtil.saveLog("알람 매니저에 ManageTripZoneGeofenceWorker 가 작동하였습니다.")

        workManager.enqueue(
            OneTimeWorkRequestBuilder<ManageTripZoneGeofenceWorker>().build()
        )
        stopSelf()

        return START_NOT_STICKY
    }
}