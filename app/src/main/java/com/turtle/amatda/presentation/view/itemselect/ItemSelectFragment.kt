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

    val itemSelectViewModel: ItemSelectViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = itemSelectViewModel
        binding.itemselectRecyclerView.adapter = itemSelectAdapter

    }

}