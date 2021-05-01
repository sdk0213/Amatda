package com.turtle.amatda.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeViewPagerFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Inject
    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var amatdaFragmentStateAdapter: HomeFragmentStateAdapter

    private val tabTextList = arrayListOf("Todo", "Setting")

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
        binding.viewPager2.adapter = amatdaFragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

}
