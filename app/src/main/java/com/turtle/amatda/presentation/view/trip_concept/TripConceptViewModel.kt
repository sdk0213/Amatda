package com.turtle.amatda.presentation.view.trip_concept

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripConcept
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.SaveTripUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TripConceptViewModel @Inject constructor(
    private val saveTripUseCase: SaveTripUseCase
) : BaseViewModel() {

    private val _argsTrip = MutableLiveData<Trip>()
    val argsTrip: LiveData<Trip> get() = _argsTrip

    private val _tripConcept = MutableLiveData(TripConcept.NORMAL)
    val tripConcept: LiveData<TripConcept> get() = _tripConcept

    fun setTrip(trip: Trip) {
        _argsTrip.value = trip
    }

    private fun getTrip(): Trip {
        return Trip(
            id = _argsTrip.value?.id ?: 0L,
            title = _argsTrip.value?.title ?: "",
            type = _tripConcept.value ?: TripConcept.NORMAL,
            zoneList = _argsTrip.value?.zoneList ?: arrayListOf(TripZone()),
            date_start = _argsTrip.value?.date_start ?: Date(),
            date_end = _argsTrip.value?.date_end ?: Date(),
        )
    }

    fun updateConcept(tripConcept: TripConcept) {
        _tripConcept.value = tripConcept
    }

    fun saveTrip() {
        compositeDisposable.add(
            saveTripUseCase.execute(getTrip())
                .subscribe(
                    {
                        Log.d(TAG, "success save Trip")
                    },
                    {
                        Log.d(TAG, "failed save Trip")
                    }
                )
        )

    }

}
