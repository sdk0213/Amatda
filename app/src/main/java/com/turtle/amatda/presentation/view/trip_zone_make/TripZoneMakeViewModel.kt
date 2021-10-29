package com.turtle.amatda.presentation.view.trip_zone_make

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.turtle.amatda.domain.model.*
import com.turtle.amatda.domain.usecases.SaveTripUseCase
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TripZoneMakeViewModel @Inject constructor(
    private val saveTripUseCase: SaveTripUseCase
) : BaseViewModel() {

    private val _position = MutableLiveData<String>()
    val position: LiveData<String> get() = _position

    private val _marker = MutableLiveData<MapMarker>()
    val marker: LiveData<MapMarker> get() = _marker

    private val _cameraPosition = MutableLiveData<MapCameraPosition>()
    val cameraPosition: LiveData<MapCameraPosition> get() = _cameraPosition

    private val _currentTrip = MutableLiveData<Trip>()
    val currentTrip: LiveData<Trip> get() = _currentTrip

    private val _currentPlace = MutableLiveData<Place>()
    val currentPlace: LiveData<Place> get() = _currentPlace

    private val _placeZoneType = MutableLiveData<ZoneType>(ZoneType.NONE)
    val placeZoneType: LiveData<ZoneType> get() = _placeZoneType

    private val _placeZoneTitle = MutableLiveData<String>("메모 없음")
    val placeZoneTitle: LiveData<String> get() = _placeZoneTitle

    private val _editMode = MutableLiveData<Event<TripZone>>()
    val editMode: LiveData<Event<TripZone>> get() = _editMode

    fun initPlaceAndTrip(placeAndTrip: PlaceAndTrip) {
        // 넘겨받은 Trip 안 zoneList 목록에 넘겨받은 Place 와 같은 위도, 경도를 가졌다면 이는 기존 TripZone 을 수정하는 모드로 취급함
        //  -> 수정모드일경우에는 Place 에 선택한 TripZone 을 넣어서 넘겨주기 때문
        placeAndTrip.trip.zoneList.find {
            it.lat == placeAndTrip.place.latLng?.latitude.toString() ?: "0" && it.lon == placeAndTrip.place.latLng?.longitude.toString() ?: "0"
        }?.let {
            _currentTrip.value = Trip(
                id = placeAndTrip.trip.id,
                title = placeAndTrip.trip.title,
                type = placeAndTrip.trip.type,
                zoneList = placeAndTrip.trip.zoneList.toMutableList().apply { remove(it) },
                nightsAndDays = placeAndTrip.trip.nightsAndDays,
                date_start = placeAndTrip.trip.date_start,
                date_end = placeAndTrip.trip.date_end,
                rating = placeAndTrip.trip.rating
            )
            _currentPlace.value = placeAndTrip.place
            _editMode.value = Event(it)
        } ?: run {
            _currentTrip.value = placeAndTrip.trip
            _currentPlace.value = placeAndTrip.place
        }

    }

    fun initMap(place: Place) {
        moveCamera(
            MapCameraPosition(
                latLng = place.latLng ?: LatLng(1.0, 1.0),
                zoom = 15f
            )
        )
        addMarkerToMap(
            MapMarker(
                latLng = place.latLng ?: LatLng(1.0, 1.0),
                title = place.name ?: "위치를 찾을수 없어요"
            )
        )
        setPosition("${place.name}\n(${place.address})" ?: "주소를 찾을수 없어요")
    }

    // 카메라 이동
    private fun moveCamera(mapCameraPosition: MapCameraPosition) {
        _cameraPosition.value = mapCameraPosition
    }

    // 마커 생성
    private fun addMarkerToMap(mapMarker: MapMarker) {
        _marker.value = mapMarker
    }

    private fun setPosition(position: String) {
        _position.value = position
    }

    fun setPlaceType(type: String) {
        _placeZoneType.value = ZoneType.values().find { it.zone == type }
    }

    fun setPlaceTitle(title: String) {
        _placeZoneTitle.value = title
    }

    fun saveTripZone() {
        compositeDisposable.add(
            saveTripUseCase.execute(
                Trip(
                    id = _currentTrip.value?.id ?: 0,
                    title = _currentTrip.value?.title ?: "",
                    type = _currentTrip.value?.type ?: TripConcept.NORMAL,
                    zoneList = arrayListOf<TripZone>().apply {
                        this.addAll(_currentTrip.value?.zoneList ?: arrayListOf(TripZone()))
                        this.add(
                            TripZone(
                                id = _editMode.value?.peekContent()?.id ?: 0,
                                area = _currentPlace.value?.name ?: "",
                                addr = _currentPlace.value?.address ?: "",
                                title = _placeZoneTitle.value ?: "",
                                date = Date(),
                                lat = _currentPlace.value?.latLng?.latitude.toString() ?: "0",
                                lon = _currentPlace.value?.latLng?.longitude.toString() ?: "0",
                                zoneType = _placeZoneType.value ?: ZoneType.NONE
                            )
                        )
                    },
                    nightsAndDays = "",
                    date_start = _currentTrip.value?.date_start ?: Date(),
                    date_end = _currentTrip.value?.date_end ?: Date(),
                    rating = _currentTrip.value?.rating ?: 3
                )
            )
                .subscribe(
                    {
                        Timber.d("saveTripZone is success")
                    },
                    {
                        Timber.e("saveTripZone is failed : ${it.message}")
                    }
                )
        )
    }
}
