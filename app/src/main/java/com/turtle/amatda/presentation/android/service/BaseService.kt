package com.turtle.amatda.presentation.android.service

import android.content.Intent
import android.os.IBinder
import com.turtle.amatda.presentation.utilities.thisServiceIsForeGroundService
import dagger.android.DaggerService

abstract class BaseService : DaggerService() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {

            // 포어그라운드에서 알림창 없애기
            if (it.getBooleanExtra(thisServiceIsForeGroundService, false)) {
                intent.putExtra(thisServiceIsForeGroundService, false)
                startService(intent)
                return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }
}