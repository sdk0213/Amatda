package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.WeatherJson
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Pty
import com.turtle.amatda.domain.model.Sky
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.presentation.utilities.NORMAL_SERVICE
import com.turtle.amatda.presentation.utilities.extensions.convertToDateyyyyMMddHHmm
import retrofit2.Response
import java.util.*
import javax.inject.Inject

open class WeatherResponseMapper @Inject constructor() :
    ResponseMapper<WeatherJson, List<Weather>> {

    // 날씨 API JSON 예)
    // 기준이 날짜기준이 아닌 카테고리 기준으로 되어있음
    // fcstDate과 fcstTime 을 기준으로 정렬이 되어있지 않아서 정렬이 필요

//    {"response":{"header":{"resultCode":"00","resultMsg":"NORMAL_SERVICE"},"body":{"dataType":"JSON","items":{
//        "item":[
//        {"baseDate":"20210916","baseTime":"1400","category":"TMP","fcstDate":"20210916","fcstTime":"1500","fcstValue":"28","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"UUU","fcstDate":"20210916","fcstTime":"1500","fcstValue":"-2.9","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"VVV","fcstDate":"20210916","fcstTime":"1500","fcstValue":"-1.3","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"VEC","fcstDate":"20210916","fcstTime":"1500","fcstValue":"67","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"WSD","fcstDate":"20210916","fcstTime":"1500","fcstValue":"3.2","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"SKY","fcstDate":"20210916","fcstTime":"1500","fcstValue":"1","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"PTY","fcstDate":"20210916","fcstTime":"1500","fcstValue":"0","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"POP","fcstDate":"20210916","fcstTime":"1500","fcstValue":"0","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"PCP","fcstDate":"20210916","fcstTime":"1500","fcstValue":"강수없음","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"REH","fcstDate":"20210916","fcstTime":"1500","fcstValue":"45","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"SNO","fcstDate":"20210916","fcstTime":"1500","fcstValue":"적설없음","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"TMP","fcstDate":"20210916","fcstTime":"1600","fcstValue":"27","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"UUU","fcstDate":"20210916","fcstTime":"1600","fcstValue":"-2.2","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"VVV","fcstDate":"20210916","fcstTime":"1600","fcstValue":"-1","nx":55,"ny":127},
//        {"baseDate":"20210916","baseTime":"1400","category":"VEC","fcstDate":"20210916","fcstTime":"1600","fcstValue":"67","nx":55,"ny":127},

    override fun responseToResource(response: Response<WeatherJson>): Resource<List<Weather>> {
        // ResultCode 가 정상(0) 이 아니라면
        if (response.body()?.response?.header?.resultCode != NORMAL_SERVICE) {
            return Resource.Error(
                data = null,
                code = response.body()?.response?.header?.resultCode,
                message = response.body()?.response?.header?.resultMsg
            )
        }

        var date: Date? = null
        var tmp = 0
        var pop = 0
        var sky: Sky = Sky.Fresh
        var pty: Pty = Pty.None

        val weatherList = arrayListOf<Weather>()

        response.body()?.let { result ->
            // JSON 으로 받아오는 값들은 전부 카테고리 기준으로 정렬이 되어있어서 기준시간으로 정렬이 변경
            // 즉, 도메인 데이터(Weather)에 맞춰서 데이터를 정렬
            // JSON 값의 예는 위의 주석과 같음 모를경우 왜 이런식으로 파싱하는지 위를 참고
            result.response.body.items.item.forEach {

                // 기준시간이 변경되었을경우 ex) 20210627 1400 -> 20210627 1500
                if (date != (it.fcstDate + it.fcstTime).convertToDateyyyyMMddHHmm()) {
                    // 기준시간 입력
                    if (date == null) {
                        date = (it.fcstDate + it.fcstTime).convertToDateyyyyMMddHHmm()
                    }
                    // 기준시간이 이미 있으면 파싱한 값들 리스트로 만들기
                    else {
                        weatherList.add(Weather(date!!, tmp, pop, sky, pty))
                        date = (it.fcstDate + it.fcstTime).convertToDateyyyyMMddHHmm()
                    }
                }

                when (it.category) {
                    "TMP" -> {
                        tmp = Integer.parseInt(it.fcstValue)
                    }
                    "POP" -> {
                        pop = Integer.parseInt(it.fcstValue)
                    }
                    "SKY" -> {
                        when (it.fcstValue) {
                            "1" -> {
                                sky = Sky.Fresh
                            }
                            "3" -> {
                                sky = Sky.Cloudy
                            }
                            "4" -> {
                                sky = Sky.Overcast
                            }
                        }
                    }
                    "PTY" -> {
                        when (it.fcstValue) {
                            "0" -> {
                                pty = Pty.None
                            }
                            "1" -> {
                                pty = Pty.Rain
                            }
                            "2" -> {
                                pty = Pty.RainOrSnow
                            }
                            "3" -> {
                                pty = Pty.Snow
                            }
                            "4" -> {
                                pty = Pty.Shower
                            }
                        }

                    }
                }
            }
            return Resource.Success(
                data = weatherList,
                code = result.response.header.resultCode,
                message = result.response.header.resultMsg
            )
        }

        return Resource.Error(
            data = null,
            message = response.message() // 실패하면 HTTP 실패 메시지 전송
        )
    }
}
