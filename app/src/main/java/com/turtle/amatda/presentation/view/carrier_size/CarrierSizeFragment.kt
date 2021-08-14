package com.turtle.amatda.presentation.view.carrier_size

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierSizeBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringTimeStamp
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.carrier_type.CarrierTypeFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

class CarrierSizeFragment : BaseFragment<CarrierSizeViewModel, FragmentCarrierSizeBinding>(R.layout.fragment_carrier_size) {

    private lateinit var carrierSizeAdapter: CarrierSizeAdapter

    private val args: CarrierSizeFragmentArgs by navArgs()

    override fun init() {
        initAdapter()
    }

    private fun initAdapter() {
        carrierSizeAdapter = CarrierSizeAdapter(
            clickSize = {
                findNavController().navigate(CarrierSizeFragmentDirections.actionCarrierSizeFragmentToCarrierNameFragment(
                    if(args.carrier.name.isEmpty()){
                        Carrier(type = args.carrier.type, size = it.size)
                    } else {
                        Carrier(
                            id = args.carrier.id,
                            name = args.carrier.name,
                            date = args.carrier.date,
                            type = args.carrier.type,
                            size = it.size
                        )
                    }
                ))
            }
        )

        binding.carrierSizeRecyclerView.adapter = carrierSizeAdapter

        carrierSizeAdapter.submitList(viewModel.getCarrierSize())
    }
}