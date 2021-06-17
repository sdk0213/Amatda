package com.turtle.amatda.presentation.view.itemselect

import com.turtle.amatda.databinding.FragmentItemselectBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class ItemSelectFragment : BaseFragment<ItemSelectViewModel, FragmentItemselectBinding>() {

    private lateinit var itemSelectAdapter: ItemSelectAdapter

    override fun init() {
        initAdapter()
        subscribeItemFromDb()
    }

    fun initAdapter(){
        itemSelectAdapter = ItemSelectAdapter(
            itemClick = {
                viewModel.insertTodo(it)
            }
        )

        binding.itemselectRecyclerView.adapter = itemSelectAdapter
    }

    fun subscribeItemFromDb() {
        viewModel.getItem().observe(viewLifecycleOwner) {
            itemSelectAdapter.submitList(it)
        }
    }

}