package com.turtle.amatda.presentation.view.carrier_type

import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierTypeBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class CarrierTypeFragment : BaseFragment<CarrierTypeViewModel, FragmentCarrierTypeBinding>(R.layout.fragment_carrier_type) {

    private lateinit var carrierTypeAdapter: CarrierTypeAdapter

    override fun init() {
        initAdapter()
    }

    private fun initAdapter() {

        carrierTypeAdapter = CarrierTypeAdapter(
            clickType = {
                findNavController().navigate(CarrierTypeFragmentDirections.actionCarrierTypeFragmentToCarrierSizeFragment(
                    Carrier(name = "", date = Date(), type = it.type, size = "not yet")
                ))
            }
        )
        binding.carrierTypeRecyclerView.adapter = carrierTypeAdapter

        carrierTypeAdapter.submitList(viewModel.getCarrierType())

    }
}