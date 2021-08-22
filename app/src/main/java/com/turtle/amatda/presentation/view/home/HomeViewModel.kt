package com.turtle.amatda.presentation.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.domain.usecases.GetWeatherUseCase
import com.turtle.amatda.presentation.utilities.NORMAL_SERVICE
import com.turtle.amatda.presentation.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList: LiveData<List<Weather>> get() = _weatherList

    fun getWeather() {
        _isLoading.value = true
        compositeDisposable.add(
            getWeatherUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {   response ->
                        when(response) {
                            is Resource.Success -> {
                                when(response.data?.response?.header?.resultCode) {
                                    NORMAL_SERVICE -> {
                                        _weatherList.value = response.data.response.body.items.item
                                    }
                                    else -> {
                                        _errorMessage.value = "${response.data?.response?.header?.resultCode} + ${response.data?.response?.header?.resultMsg}"
                                    }
                                }
                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Error -> {
                                response.message
                            }
                        }
                        _isLoading.value = false
                    },
                    {
                        Log.d(TAG,"get Weather Subscribe is on Error : Exception --> ${it.message}")
                    }
                )
        )
    }

}
