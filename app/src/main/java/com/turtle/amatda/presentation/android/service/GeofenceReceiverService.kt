package com.turtle.amatda.presentation.android.service

import android.app.NotificationManager
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.GetAllTripZoneUseCase
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.notification.NotificationData
import com.turtle.amatda.presentation.android.notification.NotificationUtil
import com.turtle.amatda.presentation.utilities.*
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class GeofenceReceiverService : BaseService() {

    @Inject
    lateinit var notificationUtil: NotificationUtil

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var androidUtil: AndroidUtil

    @Inject
    lateinit var getAllTripzoneUseCase: GetAllTripZoneUseCase

    private val compositeDisposable = CompositeDisposable()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.let {

            if (it.getBooleanExtra(thisServiceIsForeGroundService, false)) {
                startForeground(
                    notificationIdOfGeofence, notificationUtil.buildNotificationView(
                        NotificationData(
                            id = notificationChannelIdOfGeofence,
                            title = it.getStringExtra(intentServiceTitle) ?: run {
                                "아마따 여행 알림"
                            },
                            text = it.getStringExtra(intentServiceText) ?: run {
                                "여행 알림 서비스가 동작중입니다."
                            },
                            onGoing = false,
                            isBigText = true,
                            shortText = "펼쳐서 진행중인 여행을 확인하세요"
                        )
                    )
                )
            }

            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                val errorMessage =
                    GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                Timber.e(errorMessage)
                return START_STICKY
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
                compositeDisposable.add(getAllTripzoneUseCase.execute()
                    .flatMapSingle {
                        Single.create<List<TripZone>> { emitter ->
                            emitter.onSuccess(it)
                        }
                    }
                    .subscribe(
                        { tripZoneList ->
                            triggeringGeofences.forEach { geofence ->
                                tripZoneList.find { tripZone ->
                                    tripZone.lat == geofence.requestId.split(amatdaSplit)[0] &&
                                            tripZone.lon == geofence.requestId.split(amatdaSplit)[1]
                                }?.let { tripZone ->
                                    Timber.d("${tripZone.area}${transitionMsg}")
                                    androidUtil.saveLog("${tripZone.area}${transitionMsg}")
                                    notificationManager.notify(
                                        notificationIdOfDefault,
                                        notificationUtil.buildNotificationView(
                                            NotificationData(
                                                id = notificationChannelIdOfDefault,
                                                title = "${tripZone.area}${transitionMsg}",
                                                text = if (transitionMsg == "를 떠나셨습니다.") "" else tripZone.title,
                                                onGoing = true,
                                                isBigText = true
                                            )
                                        )
                                    )
                                } ?: run {
                                    Timber.e("등록된 여행을 찾을수 없습니다")
                                }
                            }
                        },
                        { throwable ->
                            Timber.e("getAllTripzoneUseCase is on Error : ${throwable.message}")
                        }
                    )
                )

            } else {
                Timber.d("GeofenceTransition : $geofenceTransition")
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        androidUtil.saveLog("GeofenceReceiverService is Dead")
    }

}