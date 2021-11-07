package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Resource
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.domain.model.TourCode
import com.turtle.amatda.domain.repository.TourRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTourUseCase @Inject constructor(
    private val repository: TourRepository
) :
    SingleUseCase<Resource<List<Tour>>, TourCode>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: TourCode?): Single<Resource<List<Tour>>> {
        return repository.getTour(tourCode = params!!)
    }

}