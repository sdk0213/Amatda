package com.turtle.amatda.presentation.view.carrier

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.CarrierAndGetHasPocketNum
import com.turtle.amatda.domain.model.CarrierAndPocket
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.usecases.GetAllItemUseCase
import com.turtle.amatda.domain.usecases.GetPocketUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierViewModel @Inject constructor(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val getPocketUseCase: GetPocketUseCase
) : BaseViewModel() {

    private val _mCarrierAndGetHasItemNum = MutableLiveData<List<CarrierAndGetHasPocketNum>>()

    private val _allCarrierPocketList = MutableLiveData<List<CarrierAndPocket>>()
    val allCarrierPocketList: LiveData<List<CarrierAndPocket>> get() = _allCarrierPocketList
    private val _allItemList = MutableLiveData<List<Item>>()
    val allItemList: LiveData<List<Item>> get() = _allItemList

    val mCarrierAndGetHasPocketNum : LiveData<List<CarrierAndGetHasPocketNum>>
        get() = _mCarrierAndGetHasItemNum

    // 모든 아이템 가져오기
    fun getAllItem(){
        compositeDisposable.add(
            getPocketUseCase.execute()
                .subscribe(
                    {
                        _allCarrierPocketList.value = it
                        compositeDisposable.add(
                            getAllItemUseCase.execute()
                                .subscribe(
                                    {
                                        _allItemList.value = it
                                    },
                                    {}
                                )
                        )
                    },
                    {}
                )
        )
    }

    fun getCarrierList() {
        compositeDisposable.add(
            getPocketUseCase.execute()
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