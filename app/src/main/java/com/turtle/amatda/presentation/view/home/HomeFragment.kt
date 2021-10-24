package com.turtle.amatda.presentation.view.home

import android.Manifest
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.tedpark.tedpermission.rx2.TedRxPermission
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.service.GeofenceReceiverService
import com.turtle.amatda.presentation.utilities.thisServiceIsForeGroundService
import com.turtle.amatda.presentation.view.base.BaseFragment
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var androidUtil: AndroidUtil

    private val homeWeatherAdapter: HomeWeatherAdapter by lazy {
        HomeWeatherAdapter()
    }
    private val homeTourAdapter: HomeTourAdapter by lazy {
        HomeTourAdapter(mContext)
    }
    private val homeRestaurantAdapter: HomeRestaurantAdapter by lazy {
        HomeRestaurantAdapter(mContext)
    }
    private lateinit var permissionRx: Disposable

    override fun init() {
        requestPermission()
        view()
        viewModel()
        observer()
        service()
    }

    private fun service() {
        if (!androidUtil.isServiceRunning(GeofenceReceiverService::class.java)) {
            if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                mContext.startForegroundService(
                    Intent(
                        mContext,
                        GeofenceReceiverService::class.java
                    ).apply {
                        putExtra(thisServiceIsForeGroundService, true)
                    }
                )
            } else {
                mContext.startService(Intent(mContext, GeofenceReceiverService::class.java))
            }
        }
    }

    private fun requestPermission() {
        permissionRx = TedRxPermission.create().apply {
            setRationaleTitle(R.string.permission_request_rationale_title)
            setRationaleMessage(R.string.permission_request_rationale_message)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            } else {
                setPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            setDeniedMessage(R.string.permission_request_rationale_message)
        }
            .request()
            .subscribe(
                { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                        viewModel.getWeather()
                    } else {
                        showToast(getString(R.string.toast_cannot_get_location_no_permission))
                    }
                },
                {
                    showToast("ERROR")
                })

    }

    private fun view() {
        binding.recyclerviewHomeWeather.adapter = homeWeatherAdapter
        binding.recyclerviewHomeTour.adapter = homeTourAdapter
        binding.recyclerviewHomeRestaurant.adapter = homeRestaurantAdapter
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun observer() {
        viewModel.weatherList.observe(this@HomeFragment) {
            homeWeatherAdapter.submitList(it)
        }

        viewModel.errorMessage.observe(this@HomeFragment) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.tourList.observe(this@HomeFragment) {
            homeTourAdapter.submitList(it)
        }
        viewModel.restaurantList.observe(this@HomeFragment) {
            homeRestaurantAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        permissionRx.dispose()
        super.onDestroy()
    }
}