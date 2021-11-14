package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.UploadFile
import com.turtle.amatda.domain.repository.UserRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateUserFileUseCase @Inject constructor(private val repository: UserRepository) :
    SingleUseCase<String, UploadFile>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: UploadFile?): Single<String> {
        return repository.updateUserFile(params!!)
    }
}