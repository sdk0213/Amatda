package com.turtle.amatda.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.turtle.amatda.databinding.FragmentSettingBinding
import com.turtle.amatda.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SettingFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Inject
    lateinit var binding: FragmentSettingBinding

    @Inject
    lateinit var settingAdapter: SettingAdapter

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    val settingViewModel: SettingViewModel by viewModels()

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
        binding.settingRecyclerView.layoutManager = gridLayoutManager
        binding.settingRecyclerView.adapter = settingAdapter
    }
}