package com.turtle.amatda.presentation.view.carrier_item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.usecases.*
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringHMSTimeStamp
import com.turtle.amatda.presentation.view.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class CarrierItemViewModel @Inject constructor(
    private val getUserCarrierUseCase: GetUserCarrierUseCase,
    private val saveCarrierPocketUseCase: SaveCarrierPocketUseCase,
    private val saveCarrierItemUseCase: SaveCarrierItemUseCase,
    private val getPocketItemUseCase: GetPocketItemUseCase,
    private val deleteCarrierItemUseCase: DeleteCarrierItemUseCase,
    private val updateCarrierItemUseCase: UpdateCarrierItemUseCase
) : BaseViewModel() {

    lateinit var currentCarrier: Carrier
    var currentPocket: Pocket = Pocket(id = Date(), carrier_id = 999999999)
    lateinit var itemIdCurrentClicked: Date

    private val _isPocketExist = MutableLiveData(false)
    val isPocketExist: LiveData<Boolean> get() = _isPocketExist

    private val _pocketList = MutableLiveData<List<Pocket>>()
    val pocketList: LiveData<List<Pocket>> get() = _pocketList

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

    // 주머니를 변경할경우 기존 데이터의 Observer 를 전부 취소하고 다시 새로운 주머니를 Observable 한다.
    fun pocketIsChanged(){
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        itemIsUnClicked()
        getCarrierPocket()
    }

    fun getCarrierPocket() {
        // todo : 아이템 목록 전부 다 가져오지 말고 carrierId 의 id 값과 동일한것 목록만 가져오기
        compositeDisposable.add(
            getUserCarrierUseCase.execute()
                .map { list ->
                    list.find {
                        it.carrier.id == currentCarrier.id
                    }
                }
                .subscribe(
                    { pocketList ->
                        // 현재 지정된 포켓 주머니가 삭제되었다면 가장 최상위에 있는것으로 가져오기
                        if (pocketList?.pockets?.any { it.id == currentPocket.id } == false) {
                            pocketList.pockets.minByOrNull { it.id.time }?.let {
                                currentPocket = it
                            }
                        }
                        // 주머니 1개 이상 존재 여부
                        _isPocketExist.value = pocketList?.pockets?.isEmpty() != true
                        _pocketList.value = pocketList?.pockets
                        getPocketItems()
                    },
                    {
                        Log.e(TAG, "getCarrierList is Error ${it.message}")
                    }
                )
        )
    }

    fun addCarrierPocket() {
        compositeDisposable.add(
            saveCarrierPocketUseCase.execute(
                Pocket(
                    id = Date(),
                    name = Date().convertDateToStringHMSTimeStamp(),
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
            getPocketItemUseCase.execute(currentPocket.id)
                .subscribe(
                    { itemListFromDb ->
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
                                if (beforeItemList[i].pocket_id == itemListFromDb[j].pocket_id &&
                                    beforeItemList[i].id == itemListFromDb[j].id &&
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

                        Log.d("asdjl","makeItemList = ${makeItemList}")
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
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemMove
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
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
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemName
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
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
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemCount
        _itemList.value?.find {
            it.id == itemIdCurrentClicked
        }?.let {
            if (it.count + (if (isCountUp) 1 else -1) < 1) return
            compositeDisposable.add(
                updateCarrierItemUseCase.execute(
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
            updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemColor
            compositeDisposable.add(
                updateCarrierItemUseCase.execute(
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
        updateCarrierItemUseCase.updateType = updateCarrierItemUseCase.typeItemSize
        compositeDisposable.add(
            updateCarrierItemUseCase.execute(
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
}
