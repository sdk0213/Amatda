package com.turtle.amatda.presentation.di.module

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import com.turtle.amatda.presentation.view.home.HomeViewPagerFragment
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    @FragmentScope
    fun provideFragmentAmatdaBinding(activity: MainActivity): FragmentHomeBinding {
        return DataBindingUtil.inflate<FragmentHomeBinding>(
            activity.layoutInflater,
            R.layout.fragment_home,
            null,
            false
        )
    }

    @Provides
    @FragmentScope
    fun provideFragment(fragment: HomeViewPagerFragment): Fragment {
        return fragment
    }

}