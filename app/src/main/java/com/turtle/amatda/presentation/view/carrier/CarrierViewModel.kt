package com.turtle.amatda.presentation.view.carrier

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.CarrierAndGetHasItemNum
import com.turtle.amatda.domain.usecases.GetUserCarrierUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierViewModel @Inject constructor(
    private val getUserCarrierUseCase: GetUserCarrierUseCase,
) : BaseViewModel() {

    private val _mCarrierAndGetHasItemNum = MutableLiveData<List<CarrierAndGetHasItemNum>>()

    val mCarrierAndGetHasItemNum : LiveData<List<CarrierAndGetHasItemNum>>
        get() = _mCarrierAndGetHasItemNum

    fun getCarrierList() {
        compositeDisposable.add(
            getUserCarrierUseCase.execute()
                .map { list ->
                    list.map { carrierAndItemsEntity ->
                        CarrierAndGetHasItemNum(
                            carrier = carrierAndItemsEntity.carrier,
                            itemSize = carrierAndItemsEntity.items.size
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