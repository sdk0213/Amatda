package com.turtle.amatda.presentation.android.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.turtle.amatda.R
import com.turtle.amatda.presentation.utilities.notificationChannelIdOfDefault
import com.turtle.amatda.presentation.utilities.notificationChannelIdOfGeofence
import com.turtle.amatda.presentation.utilities.notificationChannelNameOfDefault
import com.turtle.amatda.presentation.utilities.notificationChannelNameOfGeofence
import javax.inject.Inject

class NotificationUtil @Inject constructor(
    private val notificationManager: NotificationManager,
    private val context: Context
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

            NotificationChannel(
                notificationChannelIdOfDefault,
                notificationChannelNameOfDefault,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "여행 정보 알림"
                setShowBadge(true)
            }.let {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    fun makeNotificationView(notificationData: NotificationData) : Notification =
        NotificationCompat.Builder(context, notificationData.id).apply {
            priority = NotificationCompat.PRIORITY_HIGH
            color = ContextCompat.getColor(context, R.color.amatda_main)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(notificationData.title)
            setContentText(notificationData.text)
            notificationData.onGoing?.let {
                setOngoing(notificationData.onGoing)
            }
        }.build()

}