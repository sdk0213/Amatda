package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMainViewpagerBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainViewPagerModule {

    @Provides
    @FragmentScope
    fun provideFragmentMainViewPagerModuleBinding(activity: MainActivity): FragmentMainViewpagerBinding {
        return DataBindingUtil.inflate<FragmentMainViewpagerBinding>(
            activity.layoutInflater,
            R.layout.fragment_main_viewpager,
            null,
            false
        )
    }

}