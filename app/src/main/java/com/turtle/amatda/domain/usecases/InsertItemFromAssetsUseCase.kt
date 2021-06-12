package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InsertItemFromAssetsUseCase @Inject constructor(private val repository: ItemRepository) :
    CompletableUseCase<List<Item>>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: List<Item>?): Completable {
        return repository.insertItemAll(params!!)
    }

}