package com.turtle.amatda.domain.usecases

import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.AreaCode
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.domain.repository.TourRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTourUseCase @Inject constructor(
    private val repository: TourRepository
) :
    SingleUseCase<Resource<List<Tour>>, AreaCode>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: AreaCode?): Single<Resource<List<Tour>>> {
        return repository.getTour(areaCode = params!!)
    }

}