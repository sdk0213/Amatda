package com.turtle.amatda.presentation.view.itemselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.turtle.amatda.databinding.FragmentItemselectBinding
import com.turtle.amatda.presentation.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ItemSelectFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Inject
    lateinit var binding: FragmentItemselectBinding

    @Inject
    lateinit var itemSelectAdapter: ItemSelectAdapter

    val itemSelectviewModel: ItemSelectViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = itemSelectviewModel
        binding.itemselectRecyclerView.adapter = itemSelectAdapter

        subscribeItemFromDb()
    }

    fun subscribeItemFromDb(){
        itemSelectviewModel.getItem().observe(viewLifecycleOwner){
            itemSelectAdapter.submitList(it)
        }
    }

}