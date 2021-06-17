package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentDateBinding
import com.turtle.amatda.databinding.ListItemDateBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class DateModule {

    @Provides
    @FragmentScope
    fun provideFragmentDateBinding(activity: MainActivity): FragmentDateBinding {
        return DataBindingUtil.inflate<FragmentDateBinding>(
            activity.layoutInflater,
            R.layout.fragment_date,
            null,
            false
        )
    }

}