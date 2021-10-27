package com.turtle.amatda.presentation.android.service

import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.turtle.amatda.presentation.android.notification.NotificationData
import com.turtle.amatda.presentation.android.notification.NotificationUtil
import com.turtle.amatda.presentation.utilities.*
import com.turtle.amatda.presentation.utilities.notificationChannelIdOfDefault
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GeofenceReceiverService : BaseService() {

    @Inject
    lateinit var notificationUtil: NotificationUtil

    @Inject
    lateinit var notificationManager: NotificationManager

    private val notificationView by lazy {
        notificationUtil.makeNotificationView(
            NotificationData(
                id = notificationChannelIdOfGeofence,
                title = "위치정보파악",
                text = "지오펜스 작동중",
                onGoing = false
            )
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

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
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL
            ) {

                // Get the geofences that were triggered. A single event can trigger
                // multiple geofences.
                val triggeringGeofences = geofencingEvent.triggeringGeofences

                val transitionMsg = when (geofenceTransition) {
                    Geofence.GEOFENCE_TRANSITION_DWELL -> "에 방문하였습니다."
                    Geofence.GEOFENCE_TRANSITION_EXIT -> "를 떠나셨습니다."
                    else -> "-"
                }

                // todo : 다음 상태 일때 Notification 하기
                //      1. dwell 일경우
                //      2. exit 일경우
                val str = StringBuilder("")
                triggeringGeofences.forEach { geofence ->
                    geofence.requestId.split(amatdaSplit).apply {
                        Log.d("sudeky", "${this[0]}${transitionMsg}")
                        saveRecord("${this[0]}${transitionMsg}")
                        notificationManager.notify(
                            notificationIdOfDefault, notificationUtil.makeNotificationView(
                                NotificationData(
                                    id = notificationChannelIdOfDefault,
                                    title = "${this[0]}${transitionMsg}",
                                    text = this[1],
                                    onGoing = true
                                )
                            )
                        )

                    }
                }

            } else {
                Log.d("sudeky", geofenceTransition.toString())
            }
        }

        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        saveRecord("die")
    }

    private fun saveRecord(str: String) {

        val saveFile = File(
            filesDir, "text.txt"
        )

        try {
            val now = System.currentTimeMillis() // 현재시간 받아오기
            val date = Date(now) // Date 객체 생성
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val nowTime: String = sdf.format(date)
            val buf = BufferedWriter(FileWriter(saveFile, true))
            buf.append("$nowTime ") // 날짜 쓰기
            buf.append(str) // 파일 쓰기
            buf.newLine() // 개행
            buf.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}