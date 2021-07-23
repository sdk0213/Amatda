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
    private val _removeItemList = MutableLiveData<MutableList<Item>>()
    val removeItemList: LiveData<MutableList<Item>> get() = _removeItemList
    private val _makeItemList = MutableLiveData<MutableList<Item>>()
    val makeItemList: LiveData<MutableList<Item>> get() = _makeItemList

    private val _isItemClicked = MutableLiveData(false)
    val isItemClicked: LiveData<Boolean> get() = _isItemClicked
    private val _isItemResizeClicked = MutableLiveData(false)
    val isItemResizeClicked: LiveData<Boolean> get() = _isItemResizeClicked
    private val _isItemRecountClicked = MutableLiveData(false)
    val isItemRecountClicked: LiveData<Boolean> get() = _isItemRecountClicked
    private val _isItemRecolorClicked = MutableLiveData(false)
    val isItemRecolorClicked: LiveData<Boolean> get() = _isItemRecolorClicked

    // 해당 캐리어에 저장된 아이템리스트 가져오기
    fun getCarrierItems(carrierId: Long) {
        compositeDisposable.add(
            getCarrierItemsUseCase.execute(carrierId)
                .subscribe(
                    {   itemListFromDb ->
                        val keepViewList = arrayListOf<Item>() // 유지해야하는 아이템
                        val removeViewList = arrayListOf<Item>() // 제거해야하는 아이템
                        val makeItemList = arrayListOf<Item>() // 생성해야 하는 아이템
                        val beforeItemList = mutableListOf<Item>() // 원래 아이템 리스트
                        _itemList.value?.let { list -> beforeItemList.addAll(list) } // 원래 아이템 리스트 가져오기
                        removeViewList.addAll(beforeItemList)
                        makeItemList.addAll(itemListFromDb)
                        for (i in 0 until beforeItemList.size) {
                            for (j in 0 until itemListFromDb.size) {
                                // 기존것과 동일하다면 Item 을 유지하며 변경되었다면 Item 제거후 다시 생성
                                if (beforeItemList[i].id == itemListFromDb[j].id &&
                                    beforeItemList[i].name == itemListFromDb[j].name &&
                                    beforeItemList[i].width == itemListFromDb[j].width &&
                                    beforeItemList[i].height == itemListFromDb[j].height &&
                                    beforeItemList[i].count == itemListFromDb[j].count &&
                                    beforeItemList[i].color == itemListFromDb[j].color
                                ) {
                                    keepViewList.add(beforeItemList[i])
                                    makeItemList.remove(itemListFromDb[j])
                                    break
                                }
                            }
                        }

                        // ex) 1. ex_itemList : [1, 2, 3, 4] / itemLIst : [4, 5] ( '[4]'는 유지해야하는 View )
                        // ex) 2. ex_itemList : [4] / itemLIst : [5]
                        // ex) 3. removeList : [1, 2, 3]
                        // ex) 4. Item 에서 removeItemList 지우기
                        // ex) 5. viewList : [4] / itemLIst : [5] (View 에 itemList 추가)

                        beforeItemList.retainAll(keepViewList)
                        removeViewList.removeAll(keepViewList)
                        beforeItemList.addAll(makeItemList)

                        _itemList.value = beforeItemList
                        _removeItemList.value = removeViewList
                        _makeItemList.value = makeItemList

                    },
                    {
                        Log.e(TAG, "getItems is Error ${it.message}")
                    }
                )
        )
    }

    fun addItem(itemName: String) {
        saveItem(Item(id = Date(), name = itemName, carrier_id = carrier.id))
    }

    // todo: 기존에 선택된 아이템들의 정보가 필요한데 지금 이게 Fragment 에서 처리하고 있는데 이거를 Observer 형태로 변경을 해야하나 고민인된다.
    fun removeItem(item_id: Date) {
        _itemList.value?.find {
            it.id == item_id
        }?.let { deleteItem(it) }
    }

    fun updateMove(item_id: Date, item_pos_x: Float, item_pos_y: Float) {
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemMove
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
                Item(
                    id = item_id,
                    position_x = item_pos_x,
                    position_y = item_pos_y,
                    carrier_id = carrier.id
                )
            )
                .subscribe(
                    {
                    },
                    {
                        Log.e(TAG, "insertItem is Error ${it.message}")
                    }
                )
        )
    }

    fun updateColor(){
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemColor
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
                Item(
                    id = itemIdCurrentClicked,
                    color = 0xFFFFFFFF,
                    carrier_id = carrier.id
                )
            )
                .subscribe(
                    {
                    },
                    {
                        Log.e(TAG, "updateColor is Error ${it.message}")
                    }
                )
        )
    }

    fun editItem(item_name: String) {
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemName
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
                Item(
                    id = itemIdCurrentClicked,
                    name = item_name,
                    carrier_id = carrier.id
                )
            )
                .subscribe(
                    {
                    },
                    {
                        Log.e(TAG, "insertItem is Error ${it.message}")
                    }
                )
        )

    }

    fun recountItem(isCountUp: Boolean){
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemCount
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let {
            if(it.count + (if(isCountUp) 1 else -1) < 1) return
            compositeDisposable.add(
                updateCarrierItemUseCase.execute(
                    Item(
                        id = it.id,
                        count = it.count + (if(isCountUp) 1 else -1),
                        carrier_id = it.carrier_id
                    )
                )
                    .subscribe(
                        {
                        },
                        {
                            Log.e(TAG, "insertItem is Error ${it.message}")
                        }
                    )
            )
        }
    }

    fun decreaseWidth(updateSize: Int) {
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let { updateSize(it.width - updateSize, it.height) }

    }

    fun increaseWidth(updateSize: Int) {
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let { updateSize(it.width + updateSize, it.height) }

    }

    fun decreaseHeight(updateSize: Int) {
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let { updateSize(it.width, it.height - updateSize) }

    }

    fun increaseHeight(updateSize: Int) {
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let { updateSize(it.width, it.height + updateSize) }
    }

    fun updateColor(color: Long){
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let {
            updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemColor
            compositeDisposable.add(
                updateCarrierItemUseCase.execute(
                    Item(
                        id = it.id,
                        color = color,
                        carrier_id = it.carrier_id
                    )
                )
                    .subscribe(
                        {
                        },
                        {
                            Log.e(TAG, "insert Color is Error ${it.message}")
                        }
                    )
            )

        }
    }

    private fun updateSize(width: Int, height: Int) {
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemSize
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
                Item(
                    id = itemIdCurrentClicked,
                    width = width,
                    height = height,
                    carrier_id = carrier.id
                )
            )
                .subscribe(
                    {
                    },
                    {
                        Log.e(TAG, "insertItem is Error ${it.message}")
                    }
                )
        )
    }

    private fun saveItem(item: Item) {
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

    private fun deleteItem(item: Item) {
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

    fun itemIsClicked() {
        _isItemClicked.value = true
    }

    fun itemIsUnClicked() {
        _isItemClicked.value = false
        _isItemResizeClicked.value = false
        _isItemRecountClicked.value = false
        _isItemRecolorClicked.value = false
    }

    fun itemResizeIsClicked() {
        _isItemResizeClicked.value = true
        _isItemRecountClicked.value = false
        _isItemRecolorClicked.value = false
    }

    fun itemResizeIsUnClicked() {
        _isItemResizeClicked.value = false
    }

    fun itemRecountIsClicked(){
        _isItemRecountClicked.value = true
        _isItemResizeClicked.value = false
        _isItemRecolorClicked.value = false
    }

    fun itemRecountIsUnClicked(){
        _isItemRecountClicked.value = false
    }

    fun itemRecolorIsClicked(){
        _isItemRecolorClicked.value = true
        _isItemRecountClicked.value = false
        _isItemResizeClicked.value = false
    }

    fun itemRecolorIsUnClicked(){
        _isItemRecolorClicked.value = false
    }
}
