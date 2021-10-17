package com.turtle.amatda.presentation.view.trip_concept

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripConceptBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class TripConceptFragment :
    BaseFragment<TripConceptViewModel, FragmentTripConceptBinding>(R.layout.fragment_trip_concept) {

    private val args: TripConceptFragmentArgs by navArgs()

    override fun init() {
        view()
        viewModel()
        listener()
    }

    private fun view() {
    }

    private fun viewModel() {
        binding.viewModel = viewModel
        viewModel.setTrip(args.trip)
    }

    private fun listener() {
        binding.setClickListener {
            viewModel.saveTrip()
            findNavController().navigate(R.id.action_global_view_fragment)
        }
    }
}