package com.turtle.amatda.presentation.view.carrier_item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.usecases.*
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class CarrierItemViewModel @Inject constructor(
    private val saveCarrierItemUseCase: SaveCarrierItemUseCase,
    private val getCarrierItemsUseCase: GetCarrierItemsUseCase,
    private val deleteCarrierItemUseCase: DeleteCarrierItemUseCase,
    private val updateCarrierItemUseCase: UpdateCarrierItemUseCase
) : BaseViewModel() {

    lateinit var carrier: Carrier
    lateinit var itemIdCurrentClicked: Date

    private val _itemList = MutableLiveData<MutableList<Item>>()
    val itemList : LiveData<MutableList<Item>> get() = _itemList
    private val _isItemClicked = MutableLiveData(false)
    val isItemClicked: LiveData<Boolean> get() = _isItemClicked

    // 해당 캐리어에 저장된 아이템리스트 가져오기
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

    // todo: 기존에 선택된 아이템들의 정보가 필요한데 지금 이게 Fragment 에서 처리하고 있는데 이거를 Observer 형태로 변경을 해야하나 고민인된다.
    fun removeItem(item_id: Date){
        _itemList.value?.find{
            it.id == item_id
        }?.let { deleteItem(it) }
    }

    fun editItem(item_name : String){
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(Item(id = itemIdCurrentClicked, name = item_name, carrier_id = carrier.id))
                .subscribe(
                    {
                    },
                    {
                        Log.e(TAG, "insertItem is Error ${it.message}")
                    }
                )
        )

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

    private fun deleteItem(item: Item){
        compositeDisposable.add(
            deleteCarrierItemUseCase.execute(item)
                .subscribe(
                    {
                        itemIsUnClicked()
                    },
                    {
                        Log.e(TAG, "deleteItem is Error ${it.message}")
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
