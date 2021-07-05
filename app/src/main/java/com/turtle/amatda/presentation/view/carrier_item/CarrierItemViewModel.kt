package com.turtle.amatda.presentation.view.carrier_item

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndGetHasItemNum
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.usecases.GetCarrierItemsUseCase
import com.turtle.amatda.domain.usecases.SaveCarrierItemsUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class CarrierItemViewModel @Inject constructor(
    private val saveCarrierItemsUseCase: SaveCarrierItemsUseCase,
    private val getCarrierItemsUseCase: GetCarrierItemsUseCase
) : BaseViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    private val _itemClicked = MutableLiveData<Boolean>()

    val itemList : LiveData<List<Item>>
        get() = _itemList

    val itemClicked : LiveData<Boolean>
        get() = _itemClicked

    val arrayOfItem = mutableListOf<Item>()

    lateinit var carrier: Carrier

    fun saveCarrier(){
        compositeDisposable.add(
            saveCarrierItemsUseCase.execute(arrayOfItem)
                .subscribe()
        )
    }

    fun getCarrerItems(carrierId: Long){
        compositeDisposable.add(
            getCarrierItemsUseCase.execute(carrierId)
                .take(1)
                .subscribe(
                    {
                        _itemList.value = it

                    },
                    {
                        Log.e(TAG, "getItems is Error ${it.message}")
                    }
                )
        )
    }

}
