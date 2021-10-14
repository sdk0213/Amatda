package com.turtle.amatda.presentation.view.trip_zone_make

import android.util.Log
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripZoneMakeBinding
import com.turtle.amatda.domain.model.ZoneType
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class TripZoneMakeFragment :
    BaseFragment<TripZoneMakeViewModel, FragmentTripZoneMakeBinding>(R.layout.fragment_trip_zone_make),
    OnMapReadyCallback {

    private val args: TripZoneMakeFragmentArgs by navArgs()

    private lateinit var mMap: GoogleMap

    private val supportMapFragment by lazy {
        (childFragmentManager.findFragmentById(R.id.fragment_google_map) as SupportMapFragment)
    }

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
    }

    private fun view() {
        supportMapFragment.getMapAsync(this)

        val listOfZoneType = listOf(
            ZoneType.NONE.zone,
            ZoneType.ACCOMMODATION.zone,
            ZoneType.RESTAURANT.zone,
            ZoneType.CAFE.zone,
            ZoneType.MARKET.zone,
            ZoneType.TOURISTAREA.zone,
            ZoneType.LEISUREACTIVITY.zone,
            ZoneType.FESTIVAL.zone,
            ZoneType.BUSTERMINAL.zone,
            ZoneType.STATION.zone,
            ZoneType.CAR.zone,
        )
        val adapter = ArrayAdapter(mContext, R.layout.list_item_type, listOfZoneType)
        binding.atvTripZoneMakeType.setAdapter(adapter)
    }

    private fun viewModel() {
    }

    private fun observer() {
        viewModel.cameraPosition.observe(this@TripZoneMakeFragment) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, it.zoom))
        }

        viewModel.marker.observe(this@TripZoneMakeFragment) {
            mMap.addMarker(MarkerOptions().position(it.latLng).title(it.title))
        }

        viewModel.position.observe(this@TripZoneMakeFragment) {
            binding.tvTripZoneMakeLocation.text = it
        }
    }

    private fun listener() {
        binding.btnTripCourseOk.setOnClickListener {
//            TripZoneMakeFragmentDirections.actionTripZoneMakeFragmentToTripZoneFragment(
//                TripZone (
//                    id = 0,
//                    area = args.place.name ?: "알수 없음",
//                    title = binding.tilTripZoneMakeTitle.editText.toString(),
//                    date = Date(),
//                    lat = args.place.latLng?.latitude.toString() ?: "0",
//                    lon = args.place.latLng?.longitude.toString() ?: "0",
//                    zoneType = ZoneType.values().find { it.zone == binding.atvTripZoneMakeType.text.toString()} ?: ZoneType.NONE
//                )
//            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it
            viewModel.mapInit(args.place)
        } ?: run {
            Log.d(TAG, "googleMap is Null")
        }
    }
}