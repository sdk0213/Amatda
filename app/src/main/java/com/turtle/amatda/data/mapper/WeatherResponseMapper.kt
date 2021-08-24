package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Pty
import com.turtle.amatda.domain.model.Sky
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.presentation.utilities.NORMAL_SERVICE
import com.turtle.amatda.presentation.utilities.extensions.convertToDateyyyyMMddHHmm
import retrofit2.Response
import java.util.*
import javax.inject.Inject

open class WeatherResponseMapper @Inject constructor() : ResponseMapper<WeatherResponse, List<Weather>> {

    override fun responseToResource(response : Response<WeatherResponse>): Resource<List<Weather>> {
        var date : Date? = null
        var tmp = 0
        var pop = 0
        var sky : Sky = Sky.Fresh
        var pty : Pty = Pty.None

        val weatherList = arrayListOf<Weather>()

        if(response.body()?.response?.header?.resultCode != NORMAL_SERVICE){
            return Resource.Error(
                data = null,
                code = response.body()?.response?.header?.resultCode,
                message = response.body()?.response?.header?.resultMsg
            )
        }
        response.body()?.let {result->
            result.response.body.items.item.forEach {
                if(date != (it.fcstDate + it.fcstTime).convertToDateyyyyMMddHHmm()) {
                    if(date == null){
                        date = (it.fcstDate + it.fcstTime).convertToDateyyyyMMddHHmm()
                    } else {
                        weatherList.add(Weather(date!!, tmp, pop, sky, pty))
                        date = (it.fcstDate + it.fcstTime).convertToDateyyyyMMddHHmm()
                    }
                }
                when(it.category){
                    "TMP" -> {
                        tmp = Integer.parseInt(it.fcstValue)
                    }
                    "POP" -> {
                        pop = Integer.parseInt(it.fcstValue)
                    }
                    "SKY" -> {
                        when(it.fcstValue){
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
                        when(it.fcstValue){
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