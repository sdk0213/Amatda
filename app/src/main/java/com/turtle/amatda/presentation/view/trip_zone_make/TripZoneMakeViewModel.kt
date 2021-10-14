package com.turtle.amatda.presentation.view.trip_zone_make

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.turtle.amatda.domain.model.MapCameraPosition
import com.turtle.amatda.domain.model.MapMarker
import com.turtle.amatda.domain.usecases.GetLocationUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class TripZoneMakeViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
) : BaseViewModel() {

    private val _position = MutableLiveData<String>()
    val position: LiveData<String> get() = _position

    private val _marker = MutableLiveData<MapMarker>()
    val marker: LiveData<MapMarker> get() = _marker

    private val _cameraPosition = MutableLiveData<MapCameraPosition>()
    val cameraPosition: LiveData<MapCameraPosition> get() = _cameraPosition

    fun mapInit(place: Place) {
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

}
