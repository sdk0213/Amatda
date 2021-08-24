package com.turtle.amatda.presentation.view.home

import android.util.Log
import android.widget.Toast
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringTimeStamp
import com.turtle.amatda.presentation.view.base.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    override fun init() {
        viewModel.getWeather()
        observer()
    }

    private fun observer() {
        viewModel.weatherList.observe(this) {
            it.forEach { weather ->
                Log.d(TAG, "날짜 : ${weather.date.convertDateToStringTimeStamp()}")
                Log.d(TAG, "온도 : ${weather.tmp}")
                Log.d(TAG, "강수량 : ${weather.pop}")
                Log.d(TAG, "하늘상태 : ${weather.sky.sky}")
                Log.d(TAG, "강수형태 : ${weather.pty.pty}")
                Log.d(TAG, "---------------------------------")
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                errorMessage.let {
                    Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}