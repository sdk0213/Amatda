package com.turtle.amatda.presentation.view.trip_zone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.*
import com.turtle.amatda.domain.usecases.GetLocationUseCase
import com.turtle.amatda.presentation.utilities.extensions.getCountDay
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TripZoneViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
) : BaseViewModel() {

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
        array.add(TripZoneItem(ListItemType.FUNCTION, "여기를 눌러 위치를 추가하세요"))
        array.add(TripZoneItem(ListItemType.ITEM, TripZone(
            id = 0,
            area = "제천",
            title = "제천여행",
            lat = "0",
            lon = "0",
            zoneType = ZoneType.TOURISTAREA
        )))
        array.add(TripZoneItem(ListItemType.ITEM, TripZone(
            id = 1,
            area = "단양",
            title = "패러글라이딩",
            lat = "1",
            lon = "1",
            zoneType = ZoneType.LEISUREACTIVITY
        )))
        array.add(TripZoneItem(ListItemType.ITEM, TripZone(
            id = 2,
            area = "대전",
            title = "집으로 복귀",
            lat = "2",
            lon = "2",
            zoneType = ZoneType.ACCOMMODATION
        )))

        _tripZoneItemList.value = array
    }

    private fun setTrip(trip: Trip) {
        _argsTrip.value = trip
    }

    fun getTrip(): Trip {
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

    private fun calDate() {
        val startDate = _argsTrip.value?.date_start ?: Date()
        val endDate = _argsTrip.value?.date_end ?: Date()
        _tripDays.value = startDate.getCountDay(endDate)
    }

}
