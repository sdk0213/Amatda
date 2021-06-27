package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierNameBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class CarrierNameModule {

    @Provides
    @FragmentScope
    fun provideFragmentCarrierNameBinding(activity: MainActivity): FragmentCarrierNameBinding {
        return DataBindingUtil.inflate<FragmentCarrierNameBinding>(
            activity.layoutInflater,
            R.layout.fragment_carrier_name,
            null,
            false
        )
    }

}