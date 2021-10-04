package com.turtle.amatda.presentation.view.trip_date

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.usecases.AddTripUseCase
import com.turtle.amatda.domain.usecases.GetAllTripUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TripDateViewModel @Inject constructor(
    private val addTripUseCase: AddTripUseCase
) : BaseViewModel() {

    private val _tripStartDate = MutableLiveData<Date>()
    val tripStartDate: LiveData<Date> get() = _tripStartDate

    private val _tripEndDate = MutableLiveData<Date>()
    val tripEndDate: LiveData<Date> get() = _tripEndDate

    private val _tripTitle = MutableLiveData<String>()
    val tripTitle: LiveData<String> get() = _tripTitle

    fun addTrip() {
        compositeDisposable.add(
            addTripUseCase.execute(
                Trip(
                    id = 0,
                    title = _tripTitle.value ?: "제목없음",
                    course = "부산 -> 포항 -> 영덕",
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
                        Log.d(TAG,it.message.toString())
                    }
                )
        )
    }

    fun setDate(startDate: Date, endDate: Date) {
        _tripStartDate.value = startDate
        _tripEndDate.value = endDate
    }

    fun setTitle(title: String) {
        _tripTitle.value = title
    }
}