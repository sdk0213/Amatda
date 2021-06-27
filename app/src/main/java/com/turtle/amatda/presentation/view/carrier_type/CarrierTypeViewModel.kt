package com.turtle.amatda.presentation.view.carrier_type

import com.turtle.amatda.domain.model.CarrierType
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierTypeViewModel @Inject constructor() : BaseViewModel() {

    fun getCarrierType() : List<CarrierType>{
        return listOf(
            CarrierType("백팩"),
            CarrierType("캐리어"),
            CarrierType("크로스백"),
            CarrierType("핸드백")
        )
    }
}
