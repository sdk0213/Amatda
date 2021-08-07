package com.turtle.amatda.presentation.view.carrier

import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class CarrierFragment : BaseFragment<CarrierViewModel, FragmentCarrierBinding>(R.layout.fragment_carrier) {

    private lateinit var carrierAdapter: CarrierAdapter

    override fun init() {

        initAdapter()

        binding.setClickListener {
            findNavController().navigate(CarrierFragmentDirections.actionGlobalCarrierTypeFragment())
        }

        viewModel.getCarrierList()
        viewModel.mCarrierAndGetHasPocketNum.observe(this){
            carrierAdapter.submitList(it)
        }
    }

    private fun initAdapter() {
        carrierAdapter = CarrierAdapter(
            clickCarrier = {
                findNavController().navigate(CarrierFragmentDirections.actionGlobalCarrierItemFragment(it))
            }
        )
        binding.carrierRecyclerView.adapter = carrierAdapter
    }
}