package com.turtle.amatda.presentation.view.trip_date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripConcept
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.AddTripUseCase
import com.turtle.amatda.domain.usecases.GetAllTripUseCase
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.utilities.extensions.toCalenderDay
import com.turtle.amatda.presentation.utilities.getDateListBetween
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TripDateViewModel @Inject constructor(
    private val addTripUseCase: AddTripUseCase,
    private val allTripUseCase: GetAllTripUseCase
) : BaseViewModel() {

    private val _disabledDate = MutableLiveData<HashSet<CalendarDay>>()
    val disabledDate: LiveData<HashSet<CalendarDay>> get() = _disabledDate

    private val _isWrongDate = MutableLiveData<Event<Boolean>>()
    val isWrongDate: LiveData<Event<Boolean>> get() = _isWrongDate

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
        if(!checkWrongDate(startDate, endDate)){
            _tripStartDate.value = startDate
            _tripEndDate.value = Calendar.getInstance().apply {
                time = endDate
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
            }.time
        }
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

    // 기존에 계획되어있는 날짜들은 제거
    fun getAllTrip(){
        compositeDisposable.add(
            allTripUseCase.execute()
                .take(1)
                .subscribe(
                    {   tripList ->
                        // 수정모드 일경우 기존것은 제거
                        var mutableTripList = tripList.toMutableList()
                        mutableTripList.remove(_argsTrip.value)
                        _disabledDate.value = hashSetOf<CalendarDay>().apply {
                            mutableTripList.forEach { trip ->
                                getDateListBetween(trip.date_start, trip.date_end).forEach {
                                    this.add(it.toCalenderDay())
                                }
                            }
                        }
                    },
                    {
                        Timber.e("getAllTrip is Error : ${it.message}")
                    }
                )
        )
    }

    private fun checkWrongDate(startDate: Date, endDate: Date): Boolean {
        val hasWrongDate = getDateListBetween(startDate, endDate).any { date ->
            _disabledDate.value?.contains(date.toCalenderDay()) ?: false
        }
        _isWrongDate.value = Event(hasWrongDate)
        return hasWrongDate
    }
}
