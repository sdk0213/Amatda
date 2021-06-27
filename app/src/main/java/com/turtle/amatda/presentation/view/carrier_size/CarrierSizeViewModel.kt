package com.turtle.amatda.presentation.view.carrier_size

import com.turtle.amatda.domain.model.CarrierSize
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierSizeViewModel @Inject constructor() : BaseViewModel() {

    fun getCarrierSize(): List<CarrierSize> {
        return listOf(
            CarrierSize("대형"),
            CarrierSize("중형"),
            CarrierSize("소형")
        )
    }

}
