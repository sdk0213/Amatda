package com.turtle.amatda.presentation.view.trip_date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripConcept
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.AddTripUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TripDateViewModel @Inject constructor(
    private val addTripUseCase: AddTripUseCase
) : BaseViewModel() {

    private val _tripStartDate = MutableLiveData<Date>()
    val tripStartDate: LiveData<Date> get() = _tripStartDate

    private val _tripEndDate = MutableLiveData<Date>()
    val tripEndDate: LiveData<Date> get() = _tripEndDate

    private val _argsTrip = MutableLiveData<Trip>()
    val argsTrip: LiveData<Trip> get() = _argsTrip

    fun addTrip() {
        compositeDisposable.add(
            addTripUseCase.execute(
                Trip(
                    id = 0,
                    title = _argsTrip.value?.title ?: "제목없음",
                    zoneList = arrayListOf(TripZone()),
                    nightsAndDays = "2박 3일",
                    date_start = _tripStartDate.value ?: Date(),
                    date_end = _tripEndDate.value ?: Date(),
                    rating = 3
                )
            )
                .subscribe(
                    {

                    },
                    {
                        Timber.d(it)
                    }
                )
        )
    }

    fun setDate(startDate: Date, endDate: Date) {
        _tripStartDate.value = startDate
        _tripEndDate.value = endDate
    }

    fun setTrip(trip: Trip) {
        _argsTrip.value = trip
    }

    fun getTrip(): Trip {
        return Trip(
            id = _argsTrip.value?.id ?: 0L,
            title = _argsTrip.value?.title ?: "제목없음",
            zoneList = _argsTrip.value?.zoneList ?: arrayListOf(TripZone()),
            type = _argsTrip.value?.type ?: TripConcept.NORMAL,
            date_start = _tripStartDate.value ?: Date(),
            date_end = _tripEndDate.value ?: Date(),
        )
    }
}
