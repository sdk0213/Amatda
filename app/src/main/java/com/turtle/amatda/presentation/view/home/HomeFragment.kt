package com.turtle.amatda.presentation.view.home

import android.util.Log
import android.widget.Toast
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.domain.model.FcstType
import com.turtle.amatda.presentation.utilities.extensions.convertHHmm
import com.turtle.amatda.presentation.utilities.extensions.convertYYYYMMDD
import com.turtle.amatda.presentation.view.base.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    override fun init() {
        viewModel.getWeather()
        observer()
    }

    private fun observer() {
        viewModel.weatherList.observe(this) {
            var str = ""
            it.filter { weather ->
                weather.category == "POP" ||
                        weather.category == "TMP"
            }
                .map { weather ->
//                    Log.d(TAG, "observer: it.baseDate  : ${it.baseDate}")
//                    Log.d(TAG, "observer: it.baseTime  : ${it.baseTime}")

                    if(weather.category ==  "TMP"){
                        str += "${weather.fcstDate.convertYYYYMMDD()} ${weather.fcstTime.convertHHmm()}, 온도 : ${weather.fcstValue},"
                    } else {
                        str += " 강수확률 : ${weather.fcstValue}"
                        Log.d("sudeky",str)
                        str = ""
                    }
                }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                errorMessage.let {
                    Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}