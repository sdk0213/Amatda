package com.turtle.amatda.presentation.view.home

import android.Manifest
import android.widget.Toast
import com.tedpark.tedpermission.rx2.TedRxPermission
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.view.base.BaseFragment
import io.reactivex.disposables.Disposable
import java.util.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeWeatherAdapter: HomeWeatherAdapter by lazy {
        HomeWeatherAdapter()
    }
    private val homeTourAdapter: HomeTourAdapter by lazy {
        HomeTourAdapter(mContext)
    }
    private val homeRestaurantAdapter: HomeRestaurantAdapter by lazy {
        HomeRestaurantAdapter(mContext)
    }
    private lateinit var permissionRx : Disposable

    override fun init() {
        requestPermission()
        view()
        viewModel()
        observer()
    }

    private fun requestPermission() {
        permissionRx = TedRxPermission.create()
            .setRationaleTitle(R.string.permission_request_rationale_title)
            .setRationaleMessage(R.string.permission_request_rationale_message)
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .setDeniedMessage(R.string.permission_request_denied_message)
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