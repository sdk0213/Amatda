package com.turtle.amatda.presentation.view.itemselect

import com.turtle.amatda.databinding.FragmentItemselectBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class ItemSelectFragment : BaseFragment<ItemSelectViewModel, FragmentItemselectBinding>() {

    private lateinit var itemSelectAdapter: ItemSelectAdapter

    override fun init() {
        itemSelectAdapter = ItemSelectAdapter()
        binding.itemselectRecyclerView.adapter = itemSelectAdapter

        subscribeItemFromDb()
    }

    fun subscribeItemFromDb() {
        viewModel.getItem().observe(viewLifecycleOwner) {
            itemSelectAdapter.submitList(it)
        }
    }

}