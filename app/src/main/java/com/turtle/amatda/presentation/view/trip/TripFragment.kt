package com.turtle.amatda.presentation.view.trip

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.TedPermissionResult
import com.tedpark.tedpermission.rx2.TedRxPermission
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripBinding
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.presentation.android.view_data.TextViewData
import com.turtle.amatda.presentation.utilities.extensions.getNavigationResult
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.carrier.CarrierFragmentDirections
import io.reactivex.Single

class TripFragment : BaseFragment<TripViewModel, FragmentTripBinding>(R.layout.fragment_trip) {

    private lateinit var tripAdapter: TripAdapter

    private val returnKeyDialogDeleteTrip = "dialogReturnKeyDeleteTrip"

    private val permissionRx: Single<TedPermissionResult> by lazy {
        TedRxPermission.create().apply {
            setDeniedTitle(R.string.permission_request_title)
            setDeniedMessage(R.string.permission_request_denied_trip_message)
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
        }.request()
    }

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
        onBackPressed()
    }


    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {
        var isPermissionGranted = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if ((mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (mContext.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED)
            ) {
                isPermissionGranted = true
            }
        } else {
            if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                isPermissionGranted = true
            }
        }

        binding.constraintViewTripPermission.visibility =
            if (isPermissionGranted) View.GONE else View.VISIBLE
        binding.topAppBar.menu.findItem(R.id.item_add_trip).isVisible = isPermissionGranted

    }

    private fun requestPermission() {
        compositeDisposable.add(
            permissionRx
                .subscribe(
                    { tedPermissionResult ->
                        if (tedPermissionResult.isGranted) {
                            binding.topAppBar.menu.findItem(R.id.item_add_trip).isVisible = true
                            binding.constraintViewTripPermission.visibility = View.GONE
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
        tripAdapter = TripAdapter(
            clickTrip = { trip ->
                findNavController().navigate(
                    TripFragmentDirections.actionGlobalTripZoneFragment(
                        trip
                    )
                )
            },
            deleteTrip = { trip ->

                findNavController().navigate(
                    CarrierFragmentDirections.actionGlobalShowYesOrNoDialog(
                        TextViewData(
                            returnKey = trip.id.toString(),
                            text = "'${trip.title}'\n${getString(R.string.dialog_message_my_carrier_delete_trip)}"
                        )
                    )
                )
                getNavigationResult<String>(
                    id = R.id.view_fragment_main,
                    key = trip.id.toString(),
                    onResult = { RETURN ->
                        when (RETURN) {
                            DIALOG_RETURN_VALUE_OK -> {
                                viewModel.deleteTrip(trip)
                            }
                        }
                    })
            },
            editTrip = { trip ->
                findNavController().navigate(
                    TripFragmentDirections.actionGlobalTripTitleFragment(
                        trip
                    )
                )
            }
        )
        binding.recyclerviewTrip.adapter = tripAdapter
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun observer() {
        viewModel.tripList.observe(this@TripFragment) {
            tripAdapter.submitList(it)
        }
    }

    private fun listener() {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_add_trip -> {
                    findNavController().navigate(
                        TripFragmentDirections.actionGlobalTripTitleFragment(
                            Trip()
                        )
                    )
                    true
                }
                else -> {
                    true
                }
            }
        }

        binding.btnMyTripActivation.setOnClickListener {
            requestPermission()
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            binding.lifecycleOwner!!,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}