package com.turtle.amatda.presentation.android.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.turtle.amatda.presentation.utilities.notificationChannelIdOfGeofence
import com.turtle.amatda.presentation.utilities.notificationChannelNameOfGeofence
import javax.inject.Inject

class NotificationUtil @Inject constructor(
    private val notificationManager: NotificationManager
) {

    fun buildNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                notificationChannelIdOfGeofence,
                notificationChannelNameOfGeofence,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "위치 알림 채널입니다."
                setShowBadge(true)
            }.let {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

}