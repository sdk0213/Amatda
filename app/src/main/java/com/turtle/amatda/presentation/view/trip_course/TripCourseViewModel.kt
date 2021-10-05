package com.turtle.amatda.presentation.view.trip_course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.presentation.utilities.extensions.getCountDay
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TripCourseViewModel @Inject constructor() : BaseViewModel() {

    private val _argsTrip = MutableLiveData<Trip>()
    val argsTrip: LiveData<Trip> get() = _argsTrip

    private val _tripDays = MutableLiveData<Long>()
    val tripDays: LiveData<Long> get() = _tripDays

    fun init(trip: Trip){
        setTrip(trip)
        calDate()
    }

    private fun setTrip(trip: Trip) {
        _argsTrip.value = trip
    }

    fun getTrip() : Trip{
        return Trip(
            id = 0,
            title = _argsTrip.value?.title ?: "제목없음",
            course = "부산 -> 포항 -> 영덕",
            nightsAndDays = "2박 3일",
            date_start = _argsTrip.value?.date_start ?: Date(),
            date_end = _argsTrip.value?.date_end ?: Date(),
            rating = 3
        )
    }

    private fun calDate(){
        val startDate = _argsTrip.value?.date_start ?: Date()
        val endDate = _argsTrip.value?.date_end ?: Date()
        _tripDays.value = startDate.getCountDay(endDate)
    }

}
