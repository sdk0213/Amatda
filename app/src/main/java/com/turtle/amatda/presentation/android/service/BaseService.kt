package com.turtle.amatda.presentation.android.service

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.turtle.amatda.R
import com.turtle.amatda.presentation.utilities.foregroundChannelIdOfGeofence
import com.turtle.amatda.presentation.utilities.notificationChannelIdOfGeofence
import com.turtle.amatda.presentation.utilities.thisServiceIsForeGroundService

abstract class BaseService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {

            // 포어그라운드에서 알림창 없애기
            if(it.getBooleanExtra(thisServiceIsForeGroundService, false)){
                intent.putExtra(thisServiceIsForeGroundService, false)
                startService(intent)
                return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }
}