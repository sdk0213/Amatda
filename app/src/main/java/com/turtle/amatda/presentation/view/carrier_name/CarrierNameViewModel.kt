package com.turtle.amatda.presentation.view.carrier_name

import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Experience
import com.turtle.amatda.domain.usecases.AddUserCarrierUseCase
import com.turtle.amatda.domain.usecases.UpdateCarrierUseCase
import com.turtle.amatda.domain.usecases.UpdateUserExperienceUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class CarrierNameViewModel @Inject constructor(
    private val addUserCarrierUseCase: AddUserCarrierUseCase,
    private val updateCarrierUseCase: UpdateCarrierUseCase,
    private val updateUserExperienceUseCase: UpdateUserExperienceUseCase
) : BaseViewModel() {

    fun addCarrier(carrier: Carrier) {
        compositeDisposable.add(
            addUserCarrierUseCase.execute(carrier)
                .andThen(
                    updateUserExperienceUseCase.execute(Experience.ADD_CARRIER)
                )
                .subscribe(
                    {}, {
                        Timber.e("addUserCarrierUseCase is Error ${it.message}")
                    }
                )
        )
    }

    fun updateCarrier(carrier: Carrier) {
        compositeDisposable.add(
            updateCarrierUseCase.execute(carrier)
                .subscribe({

                }, {
                    Timber.e("updateCarrier is Error ${it.message}")
                })
        )
    }

}
