package com.turtle.amatda.domain.usecases

import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.ApiCallWeather
import com.turtle.amatda.domain.model.Area
import com.turtle.amatda.domain.repository.AreaRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetAreaUseCase @Inject constructor(
    private val repository: AreaRepository
) :
    SingleUseCase<Resource<List<Area>>, String>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    ) {

    override fun buildUseCaseCompletable(params: String?): Single<Resource<List<Area>>> {
        return repository.getArea(areaCode = params!!)
    }

}