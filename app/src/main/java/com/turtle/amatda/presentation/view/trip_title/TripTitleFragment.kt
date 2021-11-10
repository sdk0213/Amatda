package com.turtle.amatda.presentation.view.trip_title

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripTitleBinding
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.presentation.utilities.extensions.showKeyboard
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseFragment

class TripTitleFragment :
    BaseFragment<TripTitleViewModel, FragmentTripTitleBinding>(R.layout.fragment_trip_title) {

    private val args: TripTitleFragmentArgs by navArgs()

    override fun init() {
        view()
        listener()
    }

    private fun view() {
        binding.edtTripTitle.requestFocus()
        mContext.showKeyboard(binding.edtTripTitle)
        binding.edtTripTitle.text =
            if (args.trip.id != 0L) { // 아이템 수정 (ID 값이 0 이 아님)
                args.trip.title.toEditable()
            } else {
                "".toEditable()
            }
    }

    private fun listener() {
        binding.setClickListener {
            findNavController().navigate(
                TripTitleFragmentDirections.actionTripTitleFragmentToTripDateFragment(
                    Trip(
                        id = args.trip.id,
                        title = binding.edtTripTitle.text.toString(),
                        zoneList = args.trip.zoneList,
                        type = args.trip.type,
                        date_start = args.trip.date_start,
                        date_end = args.trip.date_end
                    )
                )
            )
        }
    }
}