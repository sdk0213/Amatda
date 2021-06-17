package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentSettingBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class SettingModule {

    @Provides
    @FragmentScope
    fun provideFragmentSettingBinding(activity: MainActivity): FragmentSettingBinding {
        return DataBindingUtil.inflate<FragmentSettingBinding>(
            activity.layoutInflater,
            R.layout.fragment_setting,
            null,
            false
        )
    }

}