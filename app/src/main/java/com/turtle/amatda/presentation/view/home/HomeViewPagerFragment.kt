package com.turtle.amatda.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayoutMediator
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider

class HomeViewPagerFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Inject
    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var homeFragmentStateAdapter: Provider<HomeFragmentStateAdapter> // https://stackoverflow.com/questions/56646711/expected-the-adapter-to-be-fresh-while-restoring-state
    private val tabTextList = arrayListOf("Todo", "Setting")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewPager2.adapter = homeFragmentStateAdapter.get()
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        binding.viewPager2.adapter = null // https://stackoverflow.com/questions/56646711/expected-the-adapter-to-be-fresh-while-restoring-state
        super.onDestroyView()
    }
}
