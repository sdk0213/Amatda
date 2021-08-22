package com.turtle.amatda.presentation.view.carrier_item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.*
import com.turtle.amatda.domain.usecases.*
import com.turtle.amatda.presentation.view.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class CarrierItemViewModel @Inject constructor(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val getPocketUseCase: GetPocketUseCase,
    private val savePocketUseCase: SavePocketUseCase,
    private val updatePocketUseCase: UpdatePocketUseCase,
    private val saveItemUseCase: SaveItemUseCase,
    private val getAllPocketAndItemUseCase: GetAllPocketAndItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
) : BaseViewModel() {

    lateinit var currentCarrier: Carrier
    var currentPocket: Pocket = Pocket(id = Date(), carrier_id = 999999999)
    lateinit var itemIdCurrentClicked: Date

    private val _isPocketExist = MutableLiveData(false)
    val isPocketExist: LiveData<Boolean> get() = _isPocketExist

    private val _allCarrierPocketList = MutableLiveData<List<CarrierAndPocket>>()
    val allCarrierPocketList: LiveData<List<CarrierAndPocket>> get() = _allCarrierPocketList
    private val _allItemList = MutableLiveData<List<Item>>()
    val allItemList: LiveData<List<Item>> get() = _allItemList

    private val _pocketList = MutableLiveData<List<Pocket>>()
    val pocketList: LiveData<List<Pocket>> get() = _pocketList

    private val _pocketAndItemSize = MutableLiveData<List<PocketAndItemSize>>()
    val pocketAndItemSize: LiveData<List<PocketAndItemSize>> get() = _pocketAndItemSize

    private val _itemList = MutableLiveData<MutableList<Item>>()
    private val _removeItemList = MutableLiveData<MutableList<Item>>()
    val removeItemList: LiveData<MutableList<Item>> get() = _removeItemList
    private val _makeItemList = MutableLiveData<MutableList<Item>>()
    val makeItemList: LiveData<MutableList<Item>> get() = _makeItemList

    private val _isItemClicked = MutableLiveData(false)
    val isItemClicked: LiveData<Boolean> get() = _isItemClicked
    private val _isItemSpeechBubbleVisible = MutableLiveData(false)
    val isItemSpeechBubbleVisible: LiveData<Boolean> get() = _isItemSpeechBubbleVisible
    private val _isItemResizeClicked = MutableLiveData(false)
    val isItemResizeClicked: LiveData<Boolean> get() = _isItemResizeClicked
    private val _isItemRecountClicked = MutableLiveData(false)
    val isItemRecountClicked: LiveData<Boolean> get() = _isItemRecountClicked

    // @deprecated 사용하지 않음
    private val _isItemRecolorClicked = MutableLiveData(false)
    val isItemRecolorClicked: LiveData<Boolean> get() = _isItemRecolorClicked
    private val _isItemRenameClicked = MutableLiveData(false)
    val isItemRenameClicked: LiveData<Boolean> get() = _isItemRenameClicked
    private val _isPocketAddClicked = MutableLiveData(false)
    val isPocketAddClicked: LiveData<Boolean> get() = _isPocketAddClicked
    private val _isPocketDeleteClicked = MutableLiveData(false)
    val isPocketDeleteClicked: LiveData<Boolean> get() = _isPocketDeleteClicked
    private val _isPocketRenameClicked = MutableLiveData(false)
    val isPocketRenameClicked: LiveData<Boolean> get() = _isPocketRenameClicked

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

    // 주머니를 변경할경우 기존 데이터의 Observer 를 전부 취소하고 다시 새로운 주머니를 Observable 한다.
    fun pocketIsChanged() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        itemIsUnClicked()
        getPocketItems()
    }

    fun addCarrierPocket() {
        compositeDisposable.add(
            savePocketUseCase.execute(
                Pocket(
                    id = Date(),
                    carrier_id = currentCarrier.id
                )
            )
                .subscribe(
                    {},
                    {
                        Log.e(TAG, "addCarrierPocket is Error ${it.message}")
                    }
                )
        )
    }

    // 해당 주머니에 저장된 아이템리스트 가져오기
    fun getPocketItems() {
        compositeDisposable.add(
            getAllPocketAndItemUseCase.execute(currentCarrier.id)
                .subscribe(
                    { pocketAndItem ->
                        val currentPocketAndItem = arrayListOf<PocketAndItemSize>()
                        pocketAndItem.map {
                            currentPocketAndItem.add(PocketAndItemSize(it.pocket, it.items.size))
                        }
                        _pocketAndItemSize.value = currentPocketAndItem

                        if (pocketAndItem?.any { it.pocket.id == currentPocket.id } == false) {
                            pocketAndItem.minByOrNull { it.pocket.id.time }?.let {
                                currentPocket = it.pocket
                            }
                        } else {
                            currentPocket = pocketAndItem?.find { it.pocket.id == currentPocket.id }?.pocket!!
                        }
                        // 주머니 1개 이상 존재 여부
                        _isPocketExist.value = pocketAndItem?.isEmpty() != true
                        val pocketListByCarrierId = arrayListOf<Pocket>()
                        pocketAndItem.map { pocketListByCarrierId.add(it.pocket) }
                        _pocketList.value = pocketListByCarrierId

                        val itemListByCurrentPocket =
                            pocketAndItem.find { it.pocket.id == currentPocket.id }?.items
                                ?: arrayListOf(Item(id = Date(), pocket_id = Date()))

                        val keepViewList = arrayListOf<Item>() // 유지해야하는 아이템
                        val removeViewList = arrayListOf<Item>() // 제거해야하는 아이템
                        val makeItemList = arrayListOf<Item>() // 생성해야 하는 아이템
                        val beforeItemList = mutableListOf<Item>() // 원래 아이템 리스트
                        _itemList.value?.let { list -> beforeItemList.addAll(list) } // 원래 아이템 리스트 가져오기
                        removeViewList.addAll(beforeItemList)
                        makeItemList.addAll(itemListByCurrentPocket)
                        for (i in 0 until beforeItemList.size) {
                            for (j in 0 until itemListByCurrentPocket.size) {
                                // 기존것과 동일하다면 Item 을 유지하며 변경되었다면 Item 제거후 다시 생성
                                if (beforeItemList[i].pocket_id == itemListByCurrentPocket[j].pocket_id &&
                                    beforeItemList[i].id == itemListByCurrentPocket[j].id &&
                                    beforeItemList[i].name == itemListByCurrentPocket[j].name &&
                                    beforeItemList[i].width == itemListByCurrentPocket[j].width &&
                                    beforeItemList[i].height == itemListByCurrentPocket[j].height &&
                                    beforeItemList[i].count == itemListByCurrentPocket[j].count &&
                                    beforeItemList[i].color == itemListByCurrentPocket[j].color
                                ) {
                                    keepViewList.add(beforeItemList[i])
                                    makeItemList.remove(itemListByCurrentPocket[j])
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

                        Log.d("asdjl", "_makeItemList.value = ${_makeItemList.value}")
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

    fun deletePocket(pocketId: Date) {
        updatePocketUseCase.updateType = updatePocketUseCase.typePocketDelete
        compositeDisposable.add(
            updatePocketUseCase.execute(
                Pocket(
                    id = pocketId,
                    carrier_id = currentCarrier.id
                )
            )
                .subscribe(
                    {},
                    {
                        Log.e(TAG, "deletePocket is Error ${it.message}")
                    }
                )
        )
    }

    fun editPocket(pocketId: Date, pocketName: String) {
        updatePocketUseCase.updateType = updatePocketUseCase.typePocketRename
        compositeDisposable.add(
            updatePocketUseCase.execute(
                Pocket(
                    id = pocketId,
                    name = pocketName,
                    carrier_id = currentCarrier.id
                )
            )
                .subscribe(
                    {},
                    {
                        Log.e(TAG, "editPocket Name to ${pocketName} is Error ${it.message}")
                    }
                )
        )
    }

    fun addItem(itemName: String, place: Int) {
        saveItem(
            Item(
                id = Date(),
                name = itemName,
                item_place = place,
                pocket_id = currentPocket.id
            )
        )
    }

    // todo: 기존에 선택된 아이템들의 정보가 필요한데 지금 이게 Fragment 에서 처리하고 있는데 이거를 Observer 형태로 변경을 해야하나 고민인된다.
    fun removeItem(item_id: Date) {
        _itemList.value?.find {
            it.id == item_id
        }?.let { deleteItem(it) }
    }

    fun updateMove(item_id: Date, item_pos_x: Float, item_pos_y: Float) {
        updateItemUseCase.updateType = updateItemUseCase.typeItemMove
        compositeDisposable.add(
            updateItemUseCase.execute(
                Item(
                    id = item_id,
                    position_x = item_pos_x,
                    position_y = item_pos_y,
                    pocket_id = currentPocket.id
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

    fun editItem(item_name: String) {
        updateItemUseCase.updateType = updateItemUseCase.typeItemName
        compositeDisposable.add(
            updateItemUseCase.execute(
                Item(
                    id = itemIdCurrentClicked,
                    name = item_name,
                    pocket_id = currentPocket.id
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

    fun recountItem(isCountUp: Boolean) {
        updateItemUseCase.updateType = updateItemUseCase.typeItemCount
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let {
            if (it.count + (if (isCountUp) 1 else -1) < 1) return
            compositeDisposable.add(
                updateItemUseCase.execute(
                    Item(
                        id = it.id,
                        count = it.count + (if (isCountUp) 1 else -1),
                        pocket_id = it.pocket_id
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

    fun updateColor(color: Long) {
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let {
            updateItemUseCase.updateType = updateItemUseCase.typeItemColor
            compositeDisposable.add(
                updateItemUseCase.execute(
                    Item(
                        id = it.id,
                        color = color,
                        pocket_id = it.pocket_id
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
        updateItemUseCase.updateType = updateItemUseCase.typeItemSize
        compositeDisposable.add(
            updateItemUseCase.execute(
                Item(
                    id = itemIdCurrentClicked,
                    width = width,
                    height = height,
                    pocket_id = currentPocket.id
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
            saveItemUseCase.execute(item)
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
            deleteItemUseCase.execute(item)
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
        if (_isItemResizeClicked.value == false &&
            _isItemRecountClicked.value == false &&
            _isItemRecolorClicked.value == false
        ) {
            _isItemSpeechBubbleVisible.value = true
        }
    }

    fun itemIsUnClicked() {
        _isItemClicked.value = false
        _isItemResizeClicked.value = false
        _isItemRecountClicked.value = false
        _isItemRecolorClicked.value = false
        _isItemSpeechBubbleVisible.value = false
        _isItemRenameClicked.value = false
    }

    fun itemResizeIsClicked() {
        _isItemResizeClicked.value = true
        _isItemRecountClicked.value = false
        _isItemRecolorClicked.value = false
        _isItemSpeechBubbleVisible.value = false
        _isItemRenameClicked.value = false
    }

    fun itemResizeIsUnClicked() {
        _isItemResizeClicked.value = false
        if (_isItemClicked.value == true) _isItemSpeechBubbleVisible.value = true
    }

    fun itemRecountIsClicked() {
        _isItemRecountClicked.value = true
        _isItemResizeClicked.value = false
        _isItemRecolorClicked.value = false
        _isItemSpeechBubbleVisible.value = false
        _isItemRenameClicked.value = false
    }

    fun itemRecountIsUnClicked() {
        _isItemRecountClicked.value = false
        if (_isItemClicked.value == true) _isItemSpeechBubbleVisible.value = true
    }

    // @deprecated 컬러 색 변경 사용하지 않음 (2021/08/05 sdk0213)
    fun itemRecolorIsClicked() {
        _isItemRecolorClicked.value = true
        _isItemRecountClicked.value = false
        _isItemResizeClicked.value = false
        _isItemSpeechBubbleVisible.value = false
        _isItemRenameClicked.value = false
    }

    fun itemRecolorIsUnClicked() {
        _isItemRecolorClicked.value = false
        if (_isItemClicked.value == true) _isItemSpeechBubbleVisible.value = true
    }

    fun itemRenameIsClicked() {
        _isItemRenameClicked.value = true
        _isItemSpeechBubbleVisible.value = true
        _isItemRecolorClicked.value = false
        _isItemRecountClicked.value = false
        _isItemResizeClicked.value = false
    }

    fun itemRenameIsUnClicked() {
        _isItemRenameClicked.value = false
        if (_isItemClicked.value == true) _isItemSpeechBubbleVisible.value = true
    }

    fun pocketAllUnClicked() {
        _isPocketRenameClicked.value = false
        _isPocketDeleteClicked.value = false
    }

    fun pocketDeleteClicked() {
        _isPocketRenameClicked.value = false
        _isPocketDeleteClicked.value = _isPocketDeleteClicked.value == false
    }

    fun pocketRenameClicked() {
        _isPocketDeleteClicked.value = false
        _isPocketRenameClicked.value = _isPocketRenameClicked.value == false
    }

}
