package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierSizeBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.carrier_size.CarrierSizeFragment
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class CarrierSizeModule {

    @Provides
    @FragmentScope
    fun provideFragmentCarrierSizeBinding(activity: MainActivity): FragmentCarrierSizeBinding {
        return DataBindingUtil.inflate<FragmentCarrierSizeBinding>(
            activity.layoutInflater,
            R.layout.fragment_carrier_size,
            null,
            false
        )
    }

}