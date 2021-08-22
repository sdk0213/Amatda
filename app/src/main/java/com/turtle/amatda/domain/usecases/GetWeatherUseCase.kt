package com.turtle.amatda.domain.usecases

import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.repository.WeatherRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import com.turtle.amatda.domain.usecases.common.SingleUseCase
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) :
    SingleUseCase<Resource<WeatherResponse>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Single<Resource<WeatherResponse>> {
        return repository.getWeather()
    }

}