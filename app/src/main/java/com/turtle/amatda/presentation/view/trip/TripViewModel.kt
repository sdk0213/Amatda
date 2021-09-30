package com.turtle.amatda.presentation.view.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.usecases.GetAllTripUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class TripViewModel @Inject constructor(
    private val getAllTripUseCase: GetAllTripUseCase
) : BaseViewModel() {

    private val _tripList = MutableLiveData<List<Trip>>()
    val tripList: LiveData<List<Trip>> get() = _tripList

    fun getAllTrip(){
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

}
