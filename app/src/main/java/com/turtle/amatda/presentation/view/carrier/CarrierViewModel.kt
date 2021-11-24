package com.turtle.amatda.presentation.view.carrier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndPocket
import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.usecases.DeleteCarrierUseCase
import com.turtle.amatda.domain.usecases.GetAllCarrierUseCase
import com.turtle.amatda.domain.usecases.GetAllItemUseCase
import com.turtle.amatda.domain.usecases.GetPocketUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class CarrierViewModel @Inject constructor(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val getPocketUseCase: GetPocketUseCase,
    private val deleteCarrierUseCase: DeleteCarrierUseCase,
    private val getAllCarrierUseCase: GetAllCarrierUseCase,
) : BaseViewModel() {

    private val _mCarrierWithPocketAndItems = MutableLiveData<List<CarrierWithPocketAndItems>>()
    val mCarrierWithPocketAndItems: LiveData<List<CarrierWithPocketAndItems>> get() = _mCarrierWithPocketAndItems

    private val _allCarrierPocketList = MutableLiveData<List<CarrierAndPocket>>()
    val allCarrierPocketList: LiveData<List<CarrierAndPocket>> get() = _allCarrierPocketList

    // 가방의 모든 아이템
    private val _allItemList = MutableLiveData<List<Item>>()
    val allItemList: LiveData<List<Item>> get() = _allItemList

    // 검색을 위한 데이터 제공하기 위해서 모든 아이템 가져오기
    fun getAllItem() {
        compositeDisposable.add(
            getPocketUseCase.execute()
                .subscribe(
                    {
                        _allCarrierPocketList.value = it
                        compositeDisposable.add(
                            getAllItemUseCase.execute()
                                .subscribe(
                                    { itemList ->
                                        _allItemList.value = itemList
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
            getAllCarrierUseCase.execute()
                .subscribe(
                    {
                        _mCarrierWithPocketAndItems.value = it
                    },
                    {
                        Timber.e("getCarrierList is Error ${it.message}")
                    }
                )
        )
    }

    fun deleteCarrier(carrier: Carrier) {
        compositeDisposable.add(
            deleteCarrierUseCase.execute(carrier)
                .subscribe(
                    {
                        Timber.d("deleteCarrier is success")
                    },
                    {
                        Timber.e("deleteCarrier is Error ${it.message}")
                    }
                )
        )
    }

}