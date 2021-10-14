package com.turtle.amatda.presentation.view.trip_zone

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripZoneBinding
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
                findNavController().navigate(
                    TripZoneFragmentDirections.actionTripZoneFragmentToTripZoneMakeFragment(
                        place
                    )
                )
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
            mContext,
            clickAddArea = {
                resultActivity.launch(
                    Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFieldList)
                        .setHint("여행지역을 선택해주세요")
                        .build(mContext)
                )
            }
        )
        binding.recyclerviewTripZone.adapter = tripZoneAdapter
    }

    private fun viewModel() {
        viewModel.init(args.trip)
    }

    private fun observer() {
        viewModel.tripZoneItemList.observe(this@TripZoneFragment) {
            tripZoneAdapter.submitList(it)
        }
    }

    private fun listener() {}
}