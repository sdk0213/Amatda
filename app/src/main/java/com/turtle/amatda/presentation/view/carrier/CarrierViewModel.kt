package com.turtle.amatda.presentation.view.carrier

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.CarrierAndGetHasPocketNum
import com.turtle.amatda.domain.usecases.GetUserCarrierUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierViewModel @Inject constructor(
    private val getUserCarrierUseCase: GetUserCarrierUseCase,
) : BaseViewModel() {

    private val _mCarrierAndGetHasItemNum = MutableLiveData<List<CarrierAndGetHasPocketNum>>()

    val mCarrierAndGetHasPocketNum : LiveData<List<CarrierAndGetHasPocketNum>>
        get() = _mCarrierAndGetHasItemNum

    fun getCarrierList() {
        compositeDisposable.add(
            getUserCarrierUseCase.execute()
                .map { list ->
                    list.map { carrierAndPocketEntity ->
                        CarrierAndGetHasPocketNum(
                            carrier = carrierAndPocketEntity.carrier,
                            pocketSize = carrierAndPocketEntity.pockets.size
                        )
                    }
                }
                .subscribe(
                    {
                        _mCarrierAndGetHasItemNum.value = it
                    },
                    {
                        Log.e(TAG, "getCarrierList is Error ${it.message}")
                    })
        )
    }

}