package com.turtle.amatda.presentation.view.trip_title

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripTitleBinding
import com.turtle.amatda.domain.model.Trip
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
        (mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            binding.edtTripTitle,
            0
        )
    }

    private fun listener() {
        binding.setClickListener {
            findNavController().navigate(
                TripTitleFragmentDirections.actionTripTitleFragmentToTripDateFragment(
                    Trip(
                        title = binding.edtTripTitle.text.toString()
                    )
                )
            )
        }
    }
}