package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.repository.PocketRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdatePocketUseCase @Inject constructor(private val repository: PocketRepository) :
    CompletableUseCase<Pocket>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    val typePocketDelete = "POCKET_DELETE"
    val typePocketRename = "POCKET_RENAME"

    lateinit var updateType: String

    override fun buildUseCaseCompletable(params: Pocket?): Completable {
        return when(updateType){
            typePocketDelete -> repository.deletePocket(params!!)
            else -> repository.updatePocketName(params!!)
        }
    }
}