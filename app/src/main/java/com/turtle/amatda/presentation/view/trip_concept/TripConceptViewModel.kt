package com.turtle.amatda.presentation.view.trip_concept

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.turtle.amatda.domain.model.Experience
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.TripConcept
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.usecases.SaveTripUseCase
import com.turtle.amatda.domain.usecases.UpdateUserExperienceUseCase
import com.turtle.amatda.presentation.android.workmanager.ManageTripZoneGeofenceWorker
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TripConceptViewModel @Inject constructor(
    private val saveTripUseCase: SaveTripUseCase,
    private val updateUserExperienceUseCase: UpdateUserExperienceUseCase,
    private val workManager: WorkManager
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
                .andThen(
                    updateUserExperienceUseCase.execute(Experience.EDIT_TRIP)
                )
                .subscribe(
                    {
                        Timber.d("saveTrip is success")
                        // 여행이 변경되었을수도 있으니 지오펜스 매니저 호출
                        workManager.enqueue(
                            OneTimeWorkRequestBuilder<ManageTripZoneGeofenceWorker>().build()
                        )
                    },
                    {
                        Timber.d("saveTrip is Error : ${it.message}")
                    }
                )
        )

    }

}
