package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class TripModule {

    @Provides
    @FragmentScope
    fun provideFragmentTripBinding(activity: MainActivity): FragmentTripBinding {
        return DataBindingUtil.inflate<FragmentTripBinding>(
            activity.layoutInflater,
            R.layout.fragment_trip,
            null,
            false
        )
    }

}