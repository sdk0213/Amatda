package com.turtle.amatda.presentation.view.trip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.usecases.DeleteTripUseCase
import com.turtle.amatda.domain.usecases.GetAllTripUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class TripViewModel @Inject constructor(
    private val getAllTripUseCase: GetAllTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase
) : BaseViewModel() {

    private val _tripList = MutableLiveData<List<Trip>>()
    val tripList: LiveData<List<Trip>> get() = _tripList

    fun getAllTrip() {
        compositeDisposable.add(
            getAllTripUseCase.execute()
                .subscribe(
                    {
                        _tripList.value = it
                    },
                    {

                    }
                )
        )
    }

    fun deleteTrip(trip: Trip) {
        compositeDisposable.add(
            deleteTripUseCase.execute(trip)
                .subscribe(
                    {
                        Log.d(TAG, "deleteTrip is success")
                    },
                    {
                        Log.d(TAG, "deleteTrip is onError")
                    }
                )
        )
    }

}
