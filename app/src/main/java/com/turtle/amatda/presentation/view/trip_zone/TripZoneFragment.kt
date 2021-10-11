package com.turtle.amatda.presentation.view.trip_zone

import android.util.Log
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripZoneBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class TripZoneFragment :
    BaseFragment<TripZoneViewModel, FragmentTripZoneBinding>(R.layout.fragment_trip_zone) {

    private val args: TripZoneFragmentArgs by navArgs()

    private val tripZoneAdapter : TripZoneAdapter by lazy {
        TripZoneAdapter(mContext)
    }

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
    }

    private fun view() {
        binding.recyclerviewTripZone.adapter = tripZoneAdapter
    }

    private fun viewModel() {
        viewModel.init(args.trip)
    }

    private fun observer() {
        viewModel.tripZoneItemList.observe(this@TripZoneFragment) {
            Log.d("sudeky","tripZoneAdapters id : ${tripZoneAdapter.hashCode()}")
            Log.d("sudeky","tripZoneAdapters id : ${tripZoneAdapter.hashCode()}")
            tripZoneAdapter.submitList(it)
        }
    }

    private fun listener() {

    }
}