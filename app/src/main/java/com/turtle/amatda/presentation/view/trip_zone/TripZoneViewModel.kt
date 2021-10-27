package com.turtle.amatda.presentation.view.trip_zone

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.turtle.amatda.domain.model.PlaceAndTrip
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.DeleteTripZoneUseCase
import com.turtle.amatda.domain.usecases.GetAllTripZoneUseCase
import com.turtle.amatda.domain.usecases.GetTripUseCase
import com.turtle.amatda.presentation.utilities.*
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject
import javax.inject.Named

class TripZoneViewModel @Inject constructor(
    private val geofencingClient: GeofencingClient,
    @Named("GeoPendingIntent")
    private val geofencePendingIntent: PendingIntent,
    private val getTripUseCase: GetTripUseCase,
    private val deleteTripZoneUseCase: DeleteTripZoneUseCase,
    private val getAllTripZoneUseCase: GetAllTripZoneUseCase
) : BaseViewModel() {

    private val _addZoneMessage = MutableLiveData<Event<Boolean>>()
    val addZoneMessage: LiveData<Event<Boolean>> get() = _addZoneMessage

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> get() = _errorMessage

    private val _currentPlaceAndTrip = MutableLiveData<Event<PlaceAndTrip>>()
    val currentPlaceAndTrip: LiveData<Event<PlaceAndTrip>> get() = _currentPlaceAndTrip

    private val _currentTrip = MutableLiveData<Trip>()
    val currentTrip: LiveData<Trip> get() = _currentTrip

    fun init(trip: Trip) {
        setTrip(trip)
        getCurrentTripFromDb(trip)
    }

    private fun setTrip(trip: Trip) {
        _currentTrip.value = trip
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentTripFromDb(trip: Trip) {
        compositeDisposable.add(
            getTripUseCase.execute(trip)
                .flatMap {
                    _currentTrip.value = it
                    getAllTripZoneUseCase.execute()
                }
                .subscribe(
                    { tripZoneList ->
                        // todo: 기존 여행지와 변화된것이 없다면 Geofence 를 재등록하면 안된다.
                        val geofenceList: MutableList<Geofence> = mutableListOf()
                        if (tripZoneList.isEmpty()) return@subscribe

                        geofencingClient.removeGeofences(geofencePendingIntent).run {
                            addOnSuccessListener {
                                Log.d(TAG,"모든 여행지역 삭제 성공")
                            }
                            addOnFailureListener {
                                Log.d(TAG,"모든 여행지역 삭제 실패")
                            }
                        }

                        tripZoneList.forEach { tripZone ->
                            geofenceList.add(
                                getGeofence(
                                    "${tripZone.area}${amatdaSplit}${tripZone.title}",
                                    LatLng(
                                        tripZone.lat.toDouble(),
                                        tripZone.lon.toDouble()
                                    )
                                )
                            )
                        }

                        geofencingClient.addGeofences(
                            getGeofencingRequest(geofenceList),
                            geofencePendingIntent
                        )
                            .run {
                                addOnSuccessListener {
                                    Log.d(TAG, "여행 등록에 성공하였습니다.")
                                }
                                addOnFailureListener {
                                    _addZoneMessage.value = Event(false)
                                }
                            }

                    },
                    {
                        val sw = StringWriter()
                        it.printStackTrace(PrintWriter(sw))
                        val exceptionAsString: String = sw.toString()
                        _errorMessage.value =
                            Event("Get Trip, TripZone failed in subscribe.onError\nmessage : ${it.message}")
                    }
                )
        )
    }

    fun deleteArea(tripZone: TripZone) {
        compositeDisposable.add(
            deleteTripZoneUseCase.execute(tripZone)
                .subscribe(
                    {
                        Log.d(TAG, "deleteTripZoneUseCase is success")
                    },
                    {
                        Log.d(TAG, "deleteTripZoneUseCase is failed")
                    }
                )
        )

    }

    fun selectedPlace(place: Place) {
        _currentPlaceAndTrip.value = Event(PlaceAndTrip(place, _currentTrip.value ?: Trip()))
    }

    private fun getGeofencingRequest(list: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER) // Geofence 이벤트는 진입시 부터 처리할 때
            addGeofences(list)    // Geofence 리스트 추가
        }.build()
    }

    private fun getGeofence(
        reqId: String,
        latLng: LatLng,
        radius: Float = GEOFENCE_RADIUS_IN_METERS // 단위 m
    ) =
        Geofence.Builder()
            .setRequestId(reqId)    // 이벤트 발생시 BroadcastReceiver에서 구분할 id
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)  // Geofence 만료 시간
            .setLoiteringDelay(GEOFENCE_LIOTERE_IN_MILLISECONDS) // 얼마나 머물면 DWELL 로 판정할지
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER            // 진입 감지시
                        or Geofence.GEOFENCE_TRANSITION_EXIT  // 이탈 감지시
                        or Geofence.GEOFENCE_TRANSITION_DWELL // 머물기
            )    // 머물기 감지시
            .build()
}
