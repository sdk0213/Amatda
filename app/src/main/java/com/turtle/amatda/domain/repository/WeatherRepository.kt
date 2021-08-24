package com.turtle.amatda.domain.repository

import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Weather
import io.reactivex.Single

interface WeatherRepository {
    fun getWeather() : Single<Resource<List<Weather>>>
}