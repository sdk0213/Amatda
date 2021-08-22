package com.turtle.amatda.presentation.view.carrier_name

import android.util.Log
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.usecases.AddUserCarrierUseCase
import com.turtle.amatda.domain.usecases.UpdateCarrierUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierNameViewModel @Inject constructor(
    private val addUserCarrierUseCase: AddUserCarrierUseCase,
    private val updateCarrierUseCase: UpdateCarrierUseCase
) : BaseViewModel() {

    fun addCarrier(carrier: Carrier) {
        compositeDisposable.add(
            addUserCarrierUseCase.execute(carrier)
                .subscribe({

                }, {
                    Log.e(TAG, "addUserCarrierUseCase is Error ${it.message}")
                })
        )
    }

    fun updateCarrier(carrier: Carrier){
        compositeDisposable.add(
            updateCarrierUseCase.execute(carrier)
                .subscribe({

                }, {
                    Log.e(TAG, "updateCarrier is Error ${it.message}")
                })
        )
    }

}
