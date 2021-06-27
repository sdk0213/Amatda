package com.turtle.amatda.presentation.view.carrier

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierBinding
import com.turtle.amatda.presentation.view.base.BaseFragment
import javax.inject.Inject

class CarrierFragment : BaseFragment<CarrierViewModel, FragmentCarrierBinding>() {

    private lateinit var carrierAdapter: CarrierAdapter

    override fun init() {

        carrierAdapter = CarrierAdapter()
        binding.carrierRecyclerView.adapter = carrierAdapter
        binding.setClickListener {
            findNavController().navigate(R.id.action_view_carrier_fragment_to_carrierTypeFragment)
        }

        viewModel.getCarrierList()
        viewModel.mCarrierAndGetHasItemNum.observe(this){
            carrierAdapter.submitList(it)
        }
    }
}