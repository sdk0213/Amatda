package com.turtle.amatda.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.domain.usecases.GetWeatherUseCase
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
                                _weatherList.value = response.data ?: arrayListOf()
                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Error -> {
                                _errorMessage.value = "Api call failed in Resource.Error\nCode : ${response.code}\nMessage : ${response.message}"
                            }
                        }
                        _isLoading.value = false
                    },
                    {
                        _errorMessage.value = "Api call failed in subscribe.onError\nCode : ${it.message}"
                    }
                )
        )
    }

}
