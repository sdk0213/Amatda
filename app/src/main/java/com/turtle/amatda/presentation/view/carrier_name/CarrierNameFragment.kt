package com.turtle.amatda.presentation.view.carrier_name

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierNameBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class CarrierNameFragment : BaseFragment<CarrierNameViewModel, FragmentCarrierNameBinding>() {

    private val args: CarrierNameFragmentArgs by navArgs()

    override fun init() {

        binding.setClickListener { view ->
            viewModel.addCarrier(
                Carrier(
                    name = binding.carrierName.text.toString(),
                    date = Date(),
                    type = args.carrier.type,
                    size = args.carrier.size
                )
            )
            findNavController().navigate(R.id.action_global_view_fragment)
        }
    }
}