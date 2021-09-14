package com.turtle.amatda.presentation.view.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.ApiCallBaseTime
import com.turtle.amatda.domain.model.ApiCallWeather
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.domain.usecases.GetLocationUseCase
import com.turtle.amatda.domain.usecases.GetWeatherUseCase
import com.turtle.amatda.presentation.utilities.convertGridToGps
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringyyyyMMddTimeStamp
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList: LiveData<List<Weather>> get() = _weatherList

    @SuppressLint("MissingPermission")
    // 현재 위치를 받아서 공공데이터 포털 API 의 날씨를 현재 위치를 기준으로 요청 하는 기능
    fun getWeather() {
        _isLoading.value = true
        compositeDisposable.add(
            getLocationUseCase.execute()
                .take(1)
                .flatMapSingle { location ->
                    getWeatherUseCase.execute(
                        ApiCallWeather(
                            nx = convertGridToGps(
                                0,
                                location.latitude,
                                location.longitude
                            ).x.toInt().toString(),
                            ny = convertGridToGps(
                                0,
                                location.latitude,
                                location.longitude
                            ).y.toInt().toString(),
                            base_date = chooseApiCallBaseDate(
                                SimpleDateFormat("HH", Locale.KOREA).format(Date()).toInt(),
                                SimpleDateFormat("mm", Locale.KOREA).format(Date()).toInt()
                            ),
                            base_time = compareNowWithApiStandard(
                                SimpleDateFormat("HH", Locale.KOREA).format(Date()).toInt(),
                                SimpleDateFormat("mm", Locale.KOREA).format(Date()).toInt()
                            )
                        )
                    )
                }
                .subscribe(
                    { response ->
                        when (response) {
                            is Resource.Success -> {
                                _weatherList.value = response.data ?: arrayListOf()
                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Error -> {
                                _errorMessage.value =
                                    "Api call failed in Resource.Error\nCode : ${response.code}\nMessage : ${response.message}"
                            }
                        }
                        _isLoading.value = false
                    },
                    {
                        val sw = StringWriter()
                        it.printStackTrace(PrintWriter(sw))
                        val exceptionAsString: String = sw.toString()
                        _errorMessage.value =
                            "Api call failed in subscribe.onError\nCode : stacktrace : \n$exceptionAsString"
                    }
                )
        )
    }

    // 만약 00:00 ~ 02:10 사이라면 전날으로 api 요청
    private fun chooseApiCallBaseDate(nowHour: Int, nowMinute: Int): String {
        val now = nowHour * 60 + nowMinute
        if (now >= 0 && now < ApiCallBaseTime.BaseTime1.standardTimeApiUpdate) {
            Calendar.getInstance().apply {
                time = Date()
                add(Calendar.DATE, -1)
                return time.convertDateToStringyyyyMMddTimeStamp()
            }

        } else {
            return Date().convertDateToStringyyyyMMddTimeStamp()
        }
    }

    private fun compareNowWithApiStandard(nowHour: Int, nowMinute: Int): ApiCallBaseTime {
        val now = nowHour * 60 + nowMinute
        if (
            now >= ApiCallBaseTime.BaseTime8.standardTimeApiUpdate ||
            now < ApiCallBaseTime.BaseTime1.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime8
        else if (
            now >= ApiCallBaseTime.BaseTime1.standardTimeApiUpdate &&
            now < ApiCallBaseTime.BaseTime2.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime1
        else if (
            now >= ApiCallBaseTime.BaseTime2.standardTimeApiUpdate &&
            now < ApiCallBaseTime.BaseTime3.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime2
        else if (
            now >= ApiCallBaseTime.BaseTime3.standardTimeApiUpdate &&
            now < ApiCallBaseTime.BaseTime4.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime3
        else if (
            now >= ApiCallBaseTime.BaseTime4.standardTimeApiUpdate &&
            now < ApiCallBaseTime.BaseTime5.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime4
        else if (
            now >= ApiCallBaseTime.BaseTime5.standardTimeApiUpdate &&
            now < ApiCallBaseTime.BaseTime6.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime5
        else if (
            now >= ApiCallBaseTime.BaseTime6.standardTimeApiUpdate &&
            now < ApiCallBaseTime.BaseTime7.standardTimeApiUpdate
        ) return ApiCallBaseTime.BaseTime6
        else {
            return ApiCallBaseTime.BaseTime7
        }
    }

}
