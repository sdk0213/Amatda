package com.turtle.amatda.presentation.view.carrier_type

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierTypeBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.carrier_size.CarrierSizeFragmentArgs
import java.util.*

class CarrierTypeFragment : BaseFragment<CarrierTypeViewModel, FragmentCarrierTypeBinding>(R.layout.fragment_carrier_type) {

    private lateinit var carrierTypeAdapter: CarrierTypeAdapter

    private val args: CarrierSizeFragmentArgs by navArgs()

    override fun init() {
        initAdapter()
    }

    private fun initAdapter() {

        carrierTypeAdapter = CarrierTypeAdapter(
            clickType = {
                findNavController().navigate(CarrierTypeFragmentDirections.actionCarrierTypeFragmentToCarrierSizeFragment(
                    if(args.carrier.name.isEmpty()) {
                        Carrier(type = it.type)
                    } else {
                        Carrier(
                            id = args.carrier.id,
                            name = args.carrier.name,
                            date = args.carrier.date,
                            type = it.type,
                            size = args.carrier.size
                        )
                    }
                ))
            }
        )
        binding.carrierTypeRecyclerView.adapter = carrierTypeAdapter

        carrierTypeAdapter.submitList(viewModel.getCarrierType())

    }
}