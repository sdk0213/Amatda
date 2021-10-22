package com.turtle.amatda.presentation.view.trip_zone

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripZoneBinding
import com.turtle.amatda.domain.model.ListItemType
import com.turtle.amatda.domain.model.TripZoneItem
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*
import javax.inject.Inject

class TripZoneFragment :
    BaseFragment<TripZoneViewModel, FragmentTripZoneBinding>(R.layout.fragment_trip_zone) {

    private val args: TripZoneFragmentArgs by navArgs()

    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(result.data as Intent)
                viewModel.selectedPlace(place)
            }
        }

    @Inject
    lateinit var placesClient: PlacesClient

    private val placeFieldList = listOf(
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.LAT_LNG
    )

    private lateinit var tripZoneAdapter: TripZoneAdapter

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
    }

    private fun view() {
        tripZoneAdapter = TripZoneAdapter(
            clickAddArea = {
                resultActivity.launch(
                    Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFieldList)
                        .setHint("여행지역을 선택해주세요")
                        .build(mContext)
                )
            },
            deleteArea = { tripZone ->
                viewModel.deleteArea(tripZone)
            },
            editArea = { tripZone ->
                // 수정이기 때문에 기존 TripZone 정보로 Place 생성
                viewModel.selectedPlace(
                    Place.builder().apply {
                        name = tripZone.area
                        address = tripZone.addr
                        latLng = LatLng(
                            tripZone.lat.toDouble(),
                            tripZone.lon.toDouble()
                        )
                        name = tripZone.title
                    }.build()
                )
            }
        )
        binding.recyclerviewTripZone.adapter = tripZoneAdapter
    }

    private fun viewModel() {
        viewModel.init(args.trip)
    }

    private fun observer() {
        viewModel.currentTrip.observe(this@TripZoneFragment) { trip ->
            val tripZoneList = arrayListOf<TripZoneItem>()
            tripZoneList.add(TripZoneItem(ListItemType.FUNCTION, "여기를 눌러 여행 지역을 추가하세요"))
            trip.zoneList.forEach { tripZone ->
                tripZoneList.add(TripZoneItem(ListItemType.ITEM, tripZone))
            }
            tripZoneAdapter.submitList(tripZoneList)
        }

        viewModel.currentPlaceAndTrip.observe(this@TripZoneFragment, EventObserver { placeAndTrip ->
            findNavController().navigate(
                TripZoneFragmentDirections.actionTripZoneFragmentToTripZoneMakeFragment(
                    placeAndTrip
                )
            )
        })

        viewModel.addZoneMessage.observe(this@TripZoneFragment, EventObserver { addZoneSuccess ->
            if (addZoneSuccess) {
                showToast(getString(R.string.toast_message_trip_zone_success))
            } else {
                showToast(getString(R.string.toast_message_trip_zone_failed))
            }
        })

        viewModel.errorMessage.observe(this@TripZoneFragment, EventObserver { errorMessage ->
            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
        })
    }

    private fun listener() {
        binding.btnTripZoneOk.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}