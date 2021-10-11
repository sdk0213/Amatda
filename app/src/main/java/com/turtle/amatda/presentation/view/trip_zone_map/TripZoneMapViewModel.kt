//package com.turtle.amatda.presentation.view.trip_zone_map
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.libraries.places.api.model.Place
//import com.turtle.amatda.domain.model.MapCameraPosition
//import com.turtle.amatda.domain.model.Trip
//import com.turtle.amatda.domain.usecases.GetLocationUseCase
//import com.turtle.amatda.presentation.utilities.extensions.getCountDay
//import com.turtle.amatda.presentation.view.base.BaseViewModel
//import java.util.*
//import javax.inject.Inject
//
//class TripZoneMapViewModel @Inject constructor(
//    private val getLocationUseCase: GetLocationUseCase,
//) : BaseViewModel() {
//
//    private val _argsTrip = MutableLiveData<Trip>()
//    val argsTrip: LiveData<Trip> get() = _argsTrip
//
//    private val _tripDays = MutableLiveData<Long>()
//    val tripDays: LiveData<Long> get() = _tripDays
//
//    private val _newPlace = MutableLiveData<Place>()
//    val newPlace: LiveData<Place> get() = _newPlace
//
//    private val _cameraPosition = MutableLiveData<MapCameraPosition>()
//    val cameraPosition: LiveData<MapCameraPosition> get() = _cameraPosition
//
//    fun init(trip: Trip) {
//        setTrip(trip)
//        calDate()
//        getLocation()
//    }
//
//    private fun setTrip(trip: Trip) {
//        _argsTrip.value = trip
//    }
//
//    fun getTrip(): Trip {
//        return Trip(
//            id = 0,
//            title = _argsTrip.value?.title ?: "제목없음",
//            course = "부산 -> 포항 -> 영덕",
//            nightsAndDays = "2박 3일",
//            date_start = _argsTrip.value?.date_start ?: Date(),
//            date_end = _argsTrip.value?.date_end ?: Date(),
//            rating = 3
//        )
//    }
//
//    private fun calDate() {
//        val startDate = _argsTrip.value?.date_start ?: Date()
//        val endDate = _argsTrip.value?.date_end ?: Date()
//        _tripDays.value = startDate.getCountDay(endDate)
//    }
//
//    private fun getLocation() {
//        compositeDisposable.add(
//            getLocationUseCase.execute()
//                .take(1)
//                .subscribe(
//                    {
//                        moveCamera(MapCameraPosition(LatLng(it.latitude, it.longitude), 13.5f))
//                    },
//                    {
//                        Log.d(TAG, "getLocation is on Error : ${it.message}")
//                    }
//                )
//        )
//    }
//
//    fun moveCamera(mapCameraPosition: MapCameraPosition) {
//        _cameraPosition.value = mapCameraPosition
//    }
//
//    fun addMarkerToMap(place: Place) {
//        _newPlace.value = place
//    }
//
//}
