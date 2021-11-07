package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Resource
import com.turtle.amatda.domain.model.ApiCallWeather
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.domain.repository.WeatherRepository
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) :
    SingleUseCase<Resource<List<Weather>>, ApiCallWeather>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: ApiCallWeather?): Single<Resource<List<Weather>>> {
        return repository.getWeather(
            nx = params!!.nx,
            ny = params.ny,
            base_date = params.base_date,
            base_time = params.base_time.standardTimeApiCall
        )
    }

}