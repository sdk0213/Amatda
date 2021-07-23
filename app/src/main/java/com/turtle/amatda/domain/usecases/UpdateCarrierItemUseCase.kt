package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateCarrierItemUseCase @Inject constructor(private val repository: ItemRepository) :
    CompletableUseCase<Item>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    val typeItemMove = "ITEM_MOVE"
    val typeItemName = "ITEM_NAME"
    val typeItemSize = "ITEM_SIZE"
    val typeItemCount = "ITEM_COUNT"
    val typeItemColor = "ITEM_COLOR"

    lateinit var updateType: String

    override fun buildUseCaseCompletable(params: Item?): Completable {
        return when(updateType){
            typeItemMove -> repository.updateItemPos(params!!)
            typeItemName -> repository.updateItemName(params!!)
            typeItemSize -> repository.updateItemSize(params!!)
            typeItemCount -> repository.updateItemCount(params!!)
            else -> repository.updateItemColor(params!!)
        }
    }
}