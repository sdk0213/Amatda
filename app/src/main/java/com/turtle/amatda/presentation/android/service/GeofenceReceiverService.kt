package com.turtle.amatda.presentation.android.service

import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiverService : BaseService() {

    // 포어그라운드 알림이 필요할때 startForeground 파라미터로 추가
//    private val notification: Notification by lazy {
//        NotificationCompat.Builder(this, notificationChannelIdOfGeofence)
//            .setColor(ContextCompat.getColor(this, R.color.amatda_main))
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("아마따 위치 알림")
//            .setContentText("진행중인 여행이 있습니다. 여행지역과 관련된 알림을 보냅니다.")
//            .setOngoing(true)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.let {

            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                val errorMessage =
                    GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                Log.e("GeofenceBR", errorMessage)
                return START_NOT_STICKY
            }

            // Get the transition type.
            val geofenceTransition = geofencingEvent.geofenceTransition    // 발생 이벤트 타입

            // Test that the reported transition was of interest.
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL
            ) {

                // Get the geofences that were triggered. A single event can trigger
                // multiple geofences.
                val triggeringGeofences = geofencingEvent.triggeringGeofences

                val transitionMsg = when (geofenceTransition) {
                    Geofence.GEOFENCE_TRANSITION_ENTER -> "Enter"
                    Geofence.GEOFENCE_TRANSITION_DWELL -> "Dwell"
                    Geofence.GEOFENCE_TRANSITION_EXIT -> "Exit"
                    else -> "-"
                }
                triggeringGeofences.forEach {
                    Log.d("sudeky", "onReceive: ${it.requestId} - $transitionMsg")
                }

            } else {
                Log.d("sudeky", geofenceTransition.toString())
            }
        }
        return START_NOT_STICKY
    }
}