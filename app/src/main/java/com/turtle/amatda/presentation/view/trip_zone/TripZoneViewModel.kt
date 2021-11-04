package com.turtle.amatda.presentation.view.trip_zone

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.libraries.places.api.model.Place
import com.turtle.amatda.domain.model.PlaceAndTrip
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.DeleteTripZoneUseCase
import com.turtle.amatda.domain.usecases.GetTripUseCase
import com.turtle.amatda.presentation.android.workmanager.ManageTripZoneGeofenceWorker
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject

class TripZoneViewModel @Inject constructor(
    private val getTripUseCase: GetTripUseCase,
    private val deleteTripZoneUseCase: DeleteTripZoneUseCase,
    private val workManager: WorkManager
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
        getCurrentTrip(trip)
    }

    private fun setTrip(trip: Trip) {
        _currentTrip.value = trip
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentTrip(trip: Trip) {
        compositeDisposable.add(
            getTripUseCase.execute(trip)
                .subscribe(
                    { currentTrip ->
                        _currentTrip.value = currentTrip
                    },
                    {
                        val sw = StringWriter()
                        it.printStackTrace(PrintWriter(sw))
                        val exceptionAsString: String = sw.toString()
                        Timber.e("getCurrentTrip is Error exception : $exceptionAsString")
                        _errorMessage.value =
                            Event("getCurrentTrip is subscribe.onError\nmessage : ${it.message}")
                    }
                )
        )
    }

    fun deleteArea(tripZone: TripZone) {
        compositeDisposable.add(
            deleteTripZoneUseCase.execute(tripZone)
                .subscribe(
                    {
                        Timber.d("deleteArea is success")
                        workManager.enqueue(
                            OneTimeWorkRequestBuilder<ManageTripZoneGeofenceWorker>().build()
                        )
                    },
                    {
                        Timber.d("deleteArea is failed : ${it.message}")
                    }
                )
        )

    }

    fun selectedPlace(place: Place) {
        _currentPlaceAndTrip.value = Event(PlaceAndTrip(place, _currentTrip.value ?: Trip()))
    }
}
