package com.turtle.amatda.presentation.view.home

import android.Manifest
import android.util.Log
import android.widget.Toast
import com.tedpark.tedpermission.rx2.TedRxPermission
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.utilities.convertGRID_GPS
import com.turtle.amatda.presentation.view.base.BaseFragment
import io.reactivex.disposables.Disposable
import java.util.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeWeatherAdapter: HomeWeatherAdapter
    private lateinit var permissionRx : Disposable

    override fun init() {
        requestPermission()
        view()
        viewModel()
        observer()
    }

    private fun requestPermission() {
        permissionRx = TedRxPermission.create()
            .setRationaleTitle("권한 요청")
            .setRationaleMessage("날씨 정보와 여행 기록을 위해서는 받아오기 위해서는 위치 권한이 필요해요.")
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .setDeniedMessage("날씨 정보와 여행 기록을 위해서는\n위치 권한이 필요해요.\n[설정] > [권한] 에서 위치 권한을 허용할 수 있어요")
            .request()
            .subscribe(
                { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                    } else {
                        showToast("위치 권한이 없어서 날씨 정보와 여행 정보를 가져올수없습니다.")
                    }
                },
                {
                    showToast("ERROR")
                })

    }

    private fun view() {
        homeWeatherAdapter = HomeWeatherAdapter()
        binding.recyclerviewHomeWeather.adapter = homeWeatherAdapter
    }

    private fun viewModel() {
        val xy = convertGRID_GPS(0, 36.3417660356751, 127.38276442361327)
        Log.d("sudeky","x : ${xy.x} / y : ${xy.y}")
        viewModel.getWeather()
    }

    private fun observer() {
        viewModel.weatherList.observe(this@HomeFragment) {
            homeWeatherAdapter.submitList(it)
        }

        viewModel.errorMessage.observe(this@HomeFragment) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                errorMessage.let {
                    Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        permissionRx.dispose()
        super.onDestroy()
    }
}