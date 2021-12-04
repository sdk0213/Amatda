package com.turtle.amatda.presentation.android.geofence

import android.annotation.SuppressLint
import android.app.PendingIntent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import com.turtle.amatda.presentation.utilities.GEOFENCE_EXPIRATION_IN_MILLISECONDS
import com.turtle.amatda.presentation.utilities.GEOFENCE_LIOTERE_IN_MILLISECONDS
import com.turtle.amatda.presentation.utilities.GEOFENCE_RADIUS_IN_METERS
import com.turtle.amatda.presentation.utilities.amatdaSplit
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class GeofenceUtil @Inject constructor(
    private val geofencingClient: GeofencingClient,
    @Named("GeoPendingIntent")
    private val geofencePendingIntent: PendingIntent
) {

    @SuppressLint("MissingPermission")
    fun registerGeofenceWithReset(geofenceList: List<Geofence>) {

        geofencingClient.removeGeofences(
            geofencePendingIntent
        ).run {
            addOnSuccessListener {
                Timber.d("모든 여행지역 삭제 성공")
            }
            addOnFailureListener {
                Timber.e("모든 여행지역 삭제 실패")
            }
        }

        geofencingClient.addGeofences(
            getGeofencingRequest(geofenceList),
            geofencePendingIntent
        ).run {
            addOnSuccessListener {
                Timber.d("여행 리마인더 등록에 성공")
            }
            addOnFailureListener {
                Timber.e("여행 리마인더 등록에 실패")
            }
        }

    }

    fun buildGeofence(
        latLng: LatLng,
        radius: Float = GEOFENCE_RADIUS_IN_METERS // 단위 m
    ): Geofence =
        Geofence.Builder()
            .setRequestId("${latLng.latitude}${amatdaSplit}${latLng.longitude}")
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)  // Geofence 만료 시간
            .setLoiteringDelay(GEOFENCE_LIOTERE_IN_MILLISECONDS) // 얼마나 머물면 DWELL 로 판정할지
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER            // 진입 감지시
                        or Geofence.GEOFENCE_TRANSITION_EXIT  // 이탈 감지시
                        or Geofence.GEOFENCE_TRANSITION_DWELL // 머물기
            )    // 머물기 감지시
            .build()


    private fun getGeofencingRequest(list: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER) // Geofence 이벤트는 진입시 부터 처리할 때
            addGeofences(list)    // Geofence 리스트 추가
        }.build()
    }
}