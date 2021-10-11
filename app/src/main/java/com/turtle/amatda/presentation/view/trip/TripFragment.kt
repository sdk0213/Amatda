package com.turtle.amatda.presentation.view.trip

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripBinding
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.presentation.view.base.BaseFragment

class TripFragment : BaseFragment<TripViewModel, FragmentTripBinding>(R.layout.fragment_trip) {

    private val tripAdapter: TripAdapter by lazy {
        TripAdapter()
    }

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
        onBackPressed()
    }

    private fun view() {
        binding.recyclerviewTrip.adapter = tripAdapter
    }

    private fun viewModel() {
        binding.viewModel = viewModel
        viewModel.getAllTrip()
    }

    private fun observer() {
        viewModel.tripList.observe(this@TripFragment){
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