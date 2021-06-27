package com.turtle.amatda.presentation.view.carrier_size

import android.util.Log
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierSize
import com.turtle.amatda.domain.usecases.AddUserCarrierUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierSizeViewModel @Inject constructor(
    private val addUserCarrierUseCase: AddUserCarrierUseCase,
) : BaseViewModel() {

    fun getCarrierSize() : List<CarrierSize>{
        return listOf(
            CarrierSize("대형"),
            CarrierSize("중형"),
            CarrierSize("소형")
        )
    }

    fun addCarrier(carrier: Carrier) {
        compositeDisposable.add(addUserCarrierUseCase.execute(carrier)
            .subscribe( {

            }, {
                Log.e(TAG, "addUserCarrierUseCase is Error ${it.message}")
            })
        )
    }
}
