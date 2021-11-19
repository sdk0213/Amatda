package com.turtle.amatda.presentation.view.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.usecases.DeleteTripUseCase
import com.turtle.amatda.domain.usecases.GetAllTripUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class TripViewModel @Inject constructor(
    private val getAllTripUseCase: GetAllTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase
) : BaseViewModel() {

    private val _permissionGrantedComplete = MutableLiveData<Boolean>()
    val permissionGrantedComplete: LiveData<Boolean> get() = _permissionGrantedComplete

    private val _tripList = MutableLiveData<List<Trip>>()
    val tripList: LiveData<List<Trip>> get() = _tripList

    init {
        getAllTrip()
    }

    fun permissionIsGranted() {
        _permissionGrantedComplete.value = true
    }

    private fun getAllTrip() {
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
                        Timber.d("deleteTrip is success")
                    },
                    {
                        Timber.e("deleteTrip is onError")
                    }
                )
        )
    }

}
