package com.turtle.amatda.presentation.view.home

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import com.gun0912.tedpermission.TedPermissionResult
import com.tedpark.tedpermission.rx2.TedRxPermission
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.view.android.PreventTouchEventOnViewPager
import com.turtle.amatda.presentation.view.base.BaseFragment
import io.reactivex.Single
import java.util.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    private val permissionRx: Single<TedPermissionResult> by lazy {
        TedRxPermission.create().apply {
            setDeniedTitle(R.string.permission_request_title)
            setDeniedMessage(R.string.permission_request_denied_my_home_message)
            setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }.request()
    }

    private val homeWeatherAdapter: HomeWeatherAdapter by lazy {
        HomeWeatherAdapter()
    }
    private val homeTourAdapter: HomeTourAdapter by lazy {
        HomeTourAdapter(mContext)
    }
    private val homeRestaurantAdapter: HomeRestaurantAdapter by lazy {
        HomeRestaurantAdapter(mContext)
    }

    override fun init() {
        view()
        viewModel()
        listener()
        observer()
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() =
        if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            binding.constraintViewHomePermission.visibility = View.VISIBLE
            binding.topAppBar.menu.findItem(R.id.item_refresh).isVisible = false
        } else {
            binding.constraintViewHomePermission.visibility = View.GONE
            binding.topAppBar.menu.findItem(R.id.item_refresh).isVisible = true
            viewModel.getWeather()
        }


    private fun requestPermission() {
        compositeDisposable.add(
            permissionRx
                .subscribe(
                    { tedPermissionResult ->
                        if (tedPermissionResult.isGranted) {
                            binding.constraintViewHomePermission.visibility = View.GONE
                        } else {
                            showToast(getString(R.string.toast_cannot_get_location_no_permission))
                        }
                    },
                    {
                        showToast("ERROR")
                    })
        )
    }

    private fun view() {
        binding.recyclerviewHomeWeather.adapter = homeWeatherAdapter
        binding.recyclerviewHomeTour.adapter = homeTourAdapter
        binding.recyclerviewHomeRestaurant.adapter = homeRestaurantAdapter
        // ViewPager2 안 Recyclerview 의 횡스크롤이 Viewpager2 에의해서 막히는 현상
        binding.recyclerviewHomeWeather.addOnItemTouchListener(PreventTouchEventOnViewPager())
        binding.recyclerviewHomeTour.addOnItemTouchListener(PreventTouchEventOnViewPager())
        binding.recyclerviewHomeRestaurant.addOnItemTouchListener(PreventTouchEventOnViewPager())
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun listener() {
        binding.btnMyHomeActivation.setOnClickListener {
            requestPermission()
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_refresh -> {
                    showToast(getString(R.string.toast_message_my_home_refresh))
                    viewModel.getWeather(true)
                    true
                }
                else -> {
                    true
                }
            }
        }
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

        viewModel.startGetWeatherApiCall.observe(this@HomeFragment, EventObserver { loading ->
            binding.tvHomeWeatherIsLoading.visibility = if (loading) View.VISIBLE else View.GONE
        })

        viewModel.startGetTourApiCall.observe(this@HomeFragment, EventObserver { loading ->
            binding.tvHomeRestaurantIsLoading.visibility = if (loading) View.VISIBLE else View.GONE
            binding.tvHomeTourIsLoading.visibility = if (loading) View.VISIBLE else View.GONE
        })
    }


}