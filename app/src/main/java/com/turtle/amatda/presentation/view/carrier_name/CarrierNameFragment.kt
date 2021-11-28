package com.turtle.amatda.presentation.view.carrier_name

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierNameBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.presentation.utilities.extensions.showKeyboard
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class CarrierNameFragment :
    BaseFragment<CarrierNameViewModel, FragmentCarrierNameBinding>(R.layout.fragment_carrier_name) {

    private val args: CarrierNameFragmentArgs by navArgs()

    var editCarrier = false

    override fun init() {

        if (args.carrier.name.isNotEmpty()) {
            binding.carrierName.text = args.carrier.name.toEditable()
            editCarrier = true
            binding.btnCarrierNameChecked.text = "가방 수정"
        }

        binding.carrierName.requestFocus()
        mContext.showKeyboard(binding.carrierName)

        binding.setClickListener {
            when (editCarrier) {
                true -> {
                    viewModel.updateCarrier(
                        Carrier(
                            id = args.carrier.id,
                            name = binding.carrierName.text.toString(),
                            date = Date(),
                            type = args.carrier.type,
                            size = args.carrier.size
                        )
                    )
                }
                false -> {
                    viewModel.addCarrier(
                        Carrier(
                            name = binding.carrierName.text.toString(),
                            date = Date(),
                            type = args.carrier.type,
                            size = args.carrier.size
                        )
                    )
                }
            }
            findNavController().navigate(R.id.action_global_view_fragment)
        }
    }
}