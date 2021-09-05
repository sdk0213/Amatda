package com.turtle.amatda.presentation.view.home

import android.util.Log
import android.widget.Toast
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringTimeStamp
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.carrier_type.CarrierTypeAdapter

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeWeatherAdapter: HomeWeatherAdapter

    override fun init() {
        viewModel.getWeather()
        view()
        observer()
    }

    private fun view(){
        homeWeatherAdapter = HomeWeatherAdapter()
        binding.recyclerviewHomeWeather.adapter = homeWeatherAdapter
    }

    private fun observer() {
        viewModel.weatherList.observe(this) {
            homeWeatherAdapter.submitList(it)
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