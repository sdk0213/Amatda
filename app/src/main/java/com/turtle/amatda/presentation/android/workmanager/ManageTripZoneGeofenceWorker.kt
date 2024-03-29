package com.turtle.amatda.presentation.android.workmanager

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.Geofence
import com.google.android.gms.maps.model.LatLng
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.turtle.amatda.domain.usecases.GetAllTripUseCase
import com.turtle.amatda.presentation.android.alarmmanger.AlarmManagerUtil
import com.turtle.amatda.presentation.android.di.factory.ChildWorkerFactory
import com.turtle.amatda.presentation.android.geofence.GeofenceUtil
import com.turtle.amatda.presentation.android.service.GeofenceReceiverService
import com.turtle.amatda.presentation.android.service.ServiceUtil
import com.turtle.amatda.presentation.utilities.amatdaSplit
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringyyMMddTimeStampWithSlash
import io.reactivex.Single
import java.util.*

// 여행지역 지오펜스 / 알림 / 서비스 생성
class ManageTripZoneGeofenceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val serviceUtil: ServiceUtil,
    private val geofenceUtil: GeofenceUtil,
    private val getAllTripUseCase: GetAllTripUseCase,
    private val alarmManagerUtil: AlarmManagerUtil
) : RxWorker(appContext, params) {

    override fun createWork(): Single<Result> {
        return getAllTripUseCase.execute()
            .firstOrError()
            .map { tripList ->
                // 현재 진행중인 여행에서 여행지역을 가져와 Geofence 로 등록 및 서비스 시작
                // 현재 진행중이지 않거나 여행지역이 없다면 제거 및 서비스 제거
                val geofenceList = mutableListOf<Geofence>()
                tripList.find { !Date().before(it.date_start) && !Date().after(it.date_end) }
                    ?.let {
                        if (it.zoneList.isNotEmpty()) {
                            it.zoneList.forEach { tripZone ->
                                geofenceList.add(
                                    geofenceUtil.buildGeofence(
                                        LatLng(
                                            tripZone.lat.toDouble(),
                                            tripZone.lon.toDouble()
                                        )
                                    )
                                )
                            }
                            serviceUtil.startService(
                                cls = GeofenceReceiverService::class.java,
                                title = "진행중인 여행이 있습니다",
                                text = "" +
                                        "여행 이름 \"${it.title}\"\n" +
                                        "여행 기간: ${it.date_start.convertDateToStringyyMMddTimeStampWithSlash()} ~ " +
                                        "${it.date_end.convertDateToStringyyMMddTimeStampWithSlash()}\n" +
                                        "여행 컨셉: \"${it.type.concept}\"\n" +
                                        "방문 예정 장소: 총 ${it.zoneList.size}개의 지역\n" +
                                        "여행 지역 방문시 작성한 메모를 리마인더 합니다."
                            )
                        } else {
                            serviceUtil.stopService(GeofenceReceiverService::class.java)
                        }
                    } ?: run {
                    // 진행중인 여행은 없지만 앞으로 계획된 여행이 있다면 전부 알람(StartWorkerService 실행) 으로 등록
                    tripList.filter { Date().before(it.date_start) }
                        .forEach { trip ->
                            alarmManagerUtil.setAndAllowWhileIdle(Calendar.getInstance().apply {
                                time = trip.date_start
                                add(Calendar.MINUTE, 15)
                                // 당일 오전 15분 00초

//                                set(Calendar.YEAR, 2021)
//                                set(Calendar.MONTH, 10)
//                                set(Calendar.DAY_OF_MONTH, 2)
//                                set(Calendar.HOUR_OF_DAY, 20)
//                                set(Calendar.MINUTE, 57)
                            })
                        }
                    serviceUtil.stopService(GeofenceReceiverService::class.java)
                }
                geofenceUtil.registerGeofenceWithReset(geofenceList = geofenceList)
                Result.success()
            }
            .onErrorReturn { Result.failure() }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}