package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.carrier.CarrierFragment
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class CarrierModule {

    @Provides
    @FragmentScope
    fun provideFragmentCarrierBinding(activity: MainActivity): FragmentCarrierBinding {
        return DataBindingUtil.inflate<FragmentCarrierBinding>(
            activity.layoutInflater,
            R.layout.fragment_carrier,
            null,
            false
        ) }


}