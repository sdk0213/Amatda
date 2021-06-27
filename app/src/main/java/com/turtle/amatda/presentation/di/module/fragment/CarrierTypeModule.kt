package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierTypeBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.carrier_type.CarrierTypeFragment
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class CarrierTypeModule {

    @Provides
    @FragmentScope
    fun provideFragmentCarrierTypeBinding(activity: MainActivity): FragmentCarrierTypeBinding {
        return DataBindingUtil.inflate<FragmentCarrierTypeBinding>(
            activity.layoutInflater,
            R.layout.fragment_carrier_type,
            null,
            false
        )
    }

}