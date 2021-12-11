package com.turtle.amatda.presentation.view.trip_zone_make

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripZoneMakeBinding
import com.turtle.amatda.domain.model.ZoneType
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.hideKeyboard
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseFragment
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import timber.log.Timber
import java.util.*

class TripZoneMakeFragment :
    BaseFragment<TripZoneMakeViewModel, FragmentTripZoneMakeBinding>(R.layout.fragment_trip_zone_make),
    OnMapReadyCallback {

    private val args: TripZoneMakeFragmentArgs by navArgs()

    private lateinit var mMap: GoogleMap

    private val supportMapFragment by lazy {
        (childFragmentManager.findFragmentById(R.id.fragment_google_map) as SupportMapFragment)
    }

    private val listOfZoneType = listOf(
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

    private val typeAdapter by lazy {
        ArrayAdapter(mContext, R.layout.list_item_type, listOfZoneType)
    }

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
    }

    private fun view() {
        supportMapFragment.getMapAsync(this)
        args.placeAndTrip.trip.zoneList.find { tripZone ->
            tripZone.lat == args.placeAndTrip.place.latLng?.latitude.toString() ?: "0" && tripZone.lon == args.placeAndTrip.place.latLng?.longitude.toString() ?: "0"
        } ?: run {
            binding.atvTripZoneMakeType.setAdapter(typeAdapter)
        }
    }

    private fun viewModel() {
    }

    private fun observer() {
        viewModel.currentPlace.observe(this@TripZoneMakeFragment) {
            viewModel.initMap(it)
        }

        viewModel.cameraPosition.observe(this@TripZoneMakeFragment) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, it.zoom))
        }

        viewModel.marker.observe(this@TripZoneMakeFragment) {
            mMap.addMarker(MarkerOptions().position(it.latLng).title(it.title))
        }

        viewModel.position.observe(this@TripZoneMakeFragment) {
            binding.tvTripZoneMakeLocation.text = it
        }

        viewModel.editMode.observe(this@TripZoneMakeFragment, EventObserver {
            // 수정 모드라면 자동 입력 최초 한번 필요
            binding.tieTripZoneMakeTitle.text = it.title.toEditable()
            viewModel.setPlaceTitle(it.title)
            binding.atvTripZoneMakeType.text = it.zoneType.zone.toEditable()
            binding.atvTripZoneMakeType.setAdapter(typeAdapter)
            binding.btnMakeTripZoneOk.text = "리마인더 수정"
            viewModel.setPlaceType(it.zoneType.zone)
        })

        viewModel.complete.observe(this@TripZoneMakeFragment, EventObserver{ done ->
            if(done){
                findNavController().navigateUp()
            }
        })
    }

    private fun listener() {

        KeyboardVisibilityEvent.setEventListener(requireActivity()) { isOpen ->
            binding.btnMakeTripZoneOk.visibility = if (isOpen) View.GONE else View.VISIBLE
            binding.tilTripZoneMakeType.visibility = if (isOpen) View.GONE else View.VISIBLE
            binding.tvTripZoneMakeLocation.visibility = if (isOpen) View.GONE else View.VISIBLE
            binding.imgViewTripCourseLocation.visibility = if (isOpen) View.GONE else View.VISIBLE
            supportMapFragment.view?.visibility = if (isOpen) View.GONE else View.VISIBLE
            // view 의 상태가 변할경우 Focusing 을 잃는문제를 위해 0.1 초뒤에 다시 포커싱
            handler.postDelayed({
                if (isOpen) binding.tilTripZoneMakeTitle.requestFocus() else binding.tilTripZoneMakeTitle.clearFocus()
            }, 330)
        }

        binding.btnMakeTripZoneOk.setOnClickListener {
            mContext.hideKeyboard(it.windowToken)
            viewModel.saveTripZone()
        }

        binding.atvTripZoneMakeType.setOnItemClickListener { _, _, position, _ ->
            viewModel.setPlaceType(listOfZoneType[position])
        }

        binding.tieTripZoneMakeTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 아무 동작 하지 않음
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 아무 동작 하지 않음
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.setPlaceTitle(s.toString())
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it
            viewModel.initPlaceAndTrip(args.placeAndTrip)
        } ?: run {
            Timber.d("googleMap is null")
        }
    }
}