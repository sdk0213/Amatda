package com.turtle.amatda.presentation.view.trip_zone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.ListItemType
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripZoneItem
import com.turtle.amatda.presentation.utilities.extensions.getCountDay
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TripZoneViewModel @Inject constructor() : BaseViewModel() {

    private val _argsTrip = MutableLiveData<Trip>()
    val argsTrip: LiveData<Trip> get() = _argsTrip

    private val _tripDays = MutableLiveData<Long>()
    val tripDays: LiveData<Long> get() = _tripDays

    private val _tripZoneItemList = MutableLiveData<List<TripZoneItem>>()
    val tripZoneItemList: LiveData<List<TripZoneItem>> get() = _tripZoneItemList

    fun init(trip: Trip) {
        setTrip(trip)
        calDate()

        val array = ArrayList<TripZoneItem>()
        array.add(TripZoneItem(ListItemType.FUNCTION, "여기를 눌러 여행 지역을 추가하세요"))
        _tripZoneItemList.value = array
    }

    private fun setTrip(trip: Trip) {
        _argsTrip.value = trip
    }

    fun getTrip(): Trip {
        return Trip(
            id = 0,
            title = _argsTrip.value?.title ?: "제목없음",
            nightsAndDays = "2박 3일",
            date_start = _argsTrip.value?.date_start ?: Date(),
            date_end = _argsTrip.value?.date_end ?: Date(),
            rating = 3
        )
    }

    private fun calDate() {
        val startDate = _argsTrip.value?.date_start ?: Date()
        val endDate = _argsTrip.value?.date_end ?: Date()
        _tripDays.value = startDate.getCountDay(endDate)
    }

}
