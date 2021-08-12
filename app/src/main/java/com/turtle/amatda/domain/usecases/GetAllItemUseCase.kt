package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAllItemUseCase @Inject constructor(private val repository: ItemRepository) :
    FlowableUseCase<List<Item>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<Item>> {
        return repository.getItemAll()
    }
}