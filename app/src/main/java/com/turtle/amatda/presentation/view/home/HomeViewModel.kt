package com.turtle.amatda.presentation.view.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.ApiCallBaseTime
import com.turtle.amatda.domain.model.ApiCallWeather
import com.turtle.amatda.domain.model.AreaCode
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.domain.usecases.GetAreaUseCase
import com.turtle.amatda.domain.usecases.GetLocationUseCase
import com.turtle.amatda.domain.usecases.GetTourUseCase
import com.turtle.amatda.domain.usecases.GetWeatherUseCase
import com.turtle.amatda.presentation.utilities.convertGridToGps
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringHHmmTimeStamp
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringMMddHHmmTimeStamp
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringyyyyMMddTimeStamp
import com.turtle.amatda.presentation.view.base.BaseViewModel
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getAreaUseCase: GetAreaUseCase,
    private val getTourUseCase: GetTourUseCase
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList: LiveData<List<Weather>> get() = _weatherList

    private val _weatherAddress = MutableLiveData<String>()
    val weatherAddress: LiveData<String> get() = _weatherAddress

    private var _areaCode = "1"
    private var _sigungucode = "1"

    fun getTour() {
        compositeDisposable.add(
            getAreaUseCase.execute("") // 처음에는 시도 코드를 가져오기 위해서 빈 값으로 요청
                .flatMap { response ->
                    when (response) {
                        is Resource.Success -> {
                            val area = response.data ?: arrayListOf()
                            val code = area.find {
                                _weatherAddress.value?.contains(it.name) ?: false
                            }?.code ?: "1"
                            _areaCode = code
                            // 지역코드를 사용하고 시군구 코드 검색
                            getAreaUseCase.execute(code)
                        }
                        is Resource.Loading -> {
                            _errorMessage.value =
                                "Api call failed in Resource.Error\nCode : ${response.code}\nMessage : ${response.message}"
                            getAreaUseCase.execute("1") // 값이 잘못되었을경우 서울로 요청
                        }
                        is Resource.Error -> {
                            _errorMessage.value =
                                "Api call failed in Resource.Error\nCode : ${response.code}\nMessage : ${response.message}"
                            getAreaUseCase.execute("1") // 값이 잘못되었을경우 서울로 요청
                        }
                    }
                }
                .flatMap { response ->
                    when (response) {
                        is Resource.Success -> {
                            val area = response.data ?: arrayListOf()
                            val code = area.find {
                                _weatherAddress.value?.contains(it.name) ?: false
                            }?.code ?: "1"
                            _sigungucode = code
                            // 관광지 검색
                            getTourUseCase.execute(
                                AreaCode(
                                    areacode = _areaCode,
                                    sigungucode = _sigungucode
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _errorMessage.value = "지역코드를 찾을수 없습니다."
                            getTourUseCase.execute(
                                AreaCode(
                                    areacode = _areaCode,
                                    sigungucode = _sigungucode
                                )
                            )
                        }
                        is Resource.Error -> {
                            _errorMessage.value = "지역코드를 찾을수 없습니다."
                            getTourUseCase.execute(
                                AreaCode(
                                    areacode = _areaCode,
                                    sigungucode = _sigungucode
                                )
                            )
                        }
                    }
                }
                .subscribe { response ->
                    when (response) {
                        is Resource.Success -> {
                            response.data?.forEach {
                                Log.d("sudeky","${it.title}")
                            }
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {
                            _errorMessage.value =
                            "Api call failed in Resource.Error\nCode : ${response.code}\nMessage : ${response.message}"
                        }
                    }
                }
        )
    }

    @SuppressLint("MissingPermission")
    // 현재 위치를 받아서 공공데이터 포털 API 의 날씨를 현재 위치를 기준으로 요청 하는 기능
    fun getWeather() {
        _isLoading.value = true
        compositeDisposable.add(
            getLocationUseCase.execute()
                .take(1)
                .flatMapSingle { location ->
                    _weatherAddress.value = location.address
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
                                val weather = response.data ?: arrayListOf()
                                _weatherList.value = weather.filter { weather ->
                                    val date = weather.date.convertDateToStringHHmmTimeStamp()
                                    // 0,3,6,9,12,15,18,21시만 출력
                                    date == "00:00" || date == "03:00" || date == "06:00" || date == "09:00" || date == "12:00" || date == "15:00" || date == "18:00" || date == "21:00"
                                }
                                getTour() // 관광지 목록 가져오기 - GPS 로 위치를 가져온다음에 수행하기위해서 현재 날씨를 가져온다음에 실행
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
                        Log.e(TAG, "getWeather on Error stacktrace : $exceptionAsString")
                        _errorMessage.value =
                            "Api call failed in subscribe.onError\nmessage : ${it.message}"
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
