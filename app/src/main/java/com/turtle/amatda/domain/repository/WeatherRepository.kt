package com.turtle.amatda.domain.repository

import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.domain.model.PocketAndItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

interface WeatherRepository {
    fun getWeather() : Single<Resource<WeatherResponse>>
}