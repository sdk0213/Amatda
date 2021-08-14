package com.turtle.amatda.presentation.view.carrier_name

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierNameBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class CarrierNameFragment : BaseFragment<CarrierNameViewModel, FragmentCarrierNameBinding>(R.layout.fragment_carrier_name) {

    private val args: CarrierNameFragmentArgs by navArgs()

    var editCarrier = false

    override fun init() {

        if(args.carrier.name.isNotEmpty()){
            binding.carrierName.text = args.carrier.name.toEditable()
            editCarrier = true
        }

        binding.carrierName.requestFocus()
        (mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            binding.carrierName,
            0
        )

        binding.setClickListener { view ->
            viewModel.addCarrier(
                Carrier(
                    id = if(editCarrier) args.carrier.id else 0,
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