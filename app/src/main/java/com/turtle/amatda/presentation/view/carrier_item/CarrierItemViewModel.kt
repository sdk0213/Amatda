package com.turtle.amatda.presentation.view.carrier_item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.usecases.GetCarrierItemsUseCase
import com.turtle.amatda.domain.usecases.SaveCarrierItemUseCase
import com.turtle.amatda.domain.usecases.SaveCarrierItemsUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class CarrierItemViewModel @Inject constructor(
    private val saveCarrierItemUseCase: SaveCarrierItemUseCase,
    private val getCarrierItemsUseCase: GetCarrierItemsUseCase
) : BaseViewModel() {

    lateinit var carrier: Carrier

    private val _itemList = MutableLiveData<MutableList<Item>>()
    val itemList : LiveData<MutableList<Item>> get() = _itemList
    private val _isItemClicked = MutableLiveData(false)
    val isItemClicked: LiveData<Boolean> get() = _isItemClicked

    fun getCarrierItems(carrierId: Long){
        compositeDisposable.add(
            getCarrierItemsUseCase.execute(carrierId)
                .subscribe(
                    {
                        _itemList.value = it as MutableList<Item>?

                    },
                    {
                        Log.e(TAG, "getItems is Error ${it.message}")
                    }
                )
        )
    }

    fun addItem(itemName: String){
        saveItem(Item(id = Date(), name = itemName, carrier_id = carrier.id))
    }

    fun moveItem(item_id: Date, itemName: String, item_pos_x: Float, item_pos_y: Float) {
        saveItem(Item(id = item_id, name = itemName, position_x = item_pos_x, position_y = item_pos_y, carrier_id = carrier.id))
    }

    private fun saveItem(item: Item){
        compositeDisposable.add(
            saveCarrierItemUseCase.execute(item)
                .subscribe(
                    {},
                    {
                        Log.e(TAG, "insertItem is Error ${it.message}")
                    }
                )
        )
    }

    fun itemIsClicked(){
        _isItemClicked.value = true
    }

    fun itemIsUnClicked(){
        _isItemClicked.value = false
    }

}
