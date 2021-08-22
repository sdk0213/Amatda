package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.PocketAndItem
import com.turtle.amatda.domain.repository.PocketRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAllPocketAndItemUseCase @Inject constructor(private val repository: PocketRepository) :
    FlowableUseCase<List<PocketAndItem>, Long>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Long?): Flowable<List<PocketAndItem>> {
        return repository.getPocketAndItem(params!!)
    }

}