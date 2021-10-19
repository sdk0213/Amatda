package com.turtle.amatda.presentation.view.trip_zone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.model.Place
import com.turtle.amatda.domain.model.PlaceAndTrip
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.DeleteTripZoneUseCase
import com.turtle.amatda.domain.usecases.GetTripUseCase
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class TripZoneViewModel @Inject constructor(
    private val getTripUseCase: GetTripUseCase,
    private val deleteTripZoneUseCase: DeleteTripZoneUseCase
) : BaseViewModel() {

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

    private fun getCurrentTripFromDb(trip: Trip) {
        compositeDisposable.add(
            getTripUseCase.execute(trip)
                .subscribe(
                    {
                        _currentTrip.value = it
                    },
                    {

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

}
