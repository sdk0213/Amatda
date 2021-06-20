package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentHomeBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    //데이터 바인딩 클래스 제공
    @Provides
    @FragmentScope
    fun provideFragmentHomeBinding(activity: MainActivity): FragmentHomeBinding {
        return DataBindingUtil.inflate<FragmentHomeBinding>(
            activity.layoutInflater,
            R.layout.fragment_home,
            null,
            false
        )
    }

}