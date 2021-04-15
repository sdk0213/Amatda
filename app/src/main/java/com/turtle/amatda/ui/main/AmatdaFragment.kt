package com.turtle.amatda.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.databinding.FragmentAmatdaBinding
import com.turtle.amatda.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AmatdaFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Inject
    lateinit var binding: FragmentAmatdaBinding

    @Inject
    lateinit var amatdaFragmentStateAdapter: AmatdaFragmentStateAdapter

    private val tabTextList = arrayListOf("HOME", "SETTING")

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
